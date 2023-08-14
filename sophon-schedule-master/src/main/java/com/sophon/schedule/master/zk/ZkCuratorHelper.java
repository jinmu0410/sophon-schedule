package com.sophon.schedule.master.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/8/9 15:01
 */
public class ZkCuratorHelper {

    public CuratorFramework curatorFramework;

    public ZkCuratorHelper(String ipsPort,String namespace){
        curatorFramework = CuratorFrameworkFactory
                .builder() // 使用工厂类来建造客户端的实例对象
                .connectString(ipsPort) // 配置zk服务器IP port
                .sessionTimeoutMs(4000)// 设定会话时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//设置及重连策略
                .namespace(namespace)//方便管理的命名空间,其实就是一级目录
                .build();//建立管道
        curatorFramework.start();//开启curator
    }


    public void closeZkCuratorConnection() {
        curatorFramework.close();
        System.out.println("关闭了");
    }

    public void createZNode(String path, String value,Boolean tempNode) throws Exception {
        CreateMode createMode = tempNode ? CreateMode.EPHEMERAL: CreateMode.PERSISTENT;
        curatorFramework
                .create()
                .creatingParentsIfNeeded()//如果是多级结点,这里声明如果需要,自动创建父节点
                .withMode(createMode)
                .forPath(path,value.getBytes());//声明结点路径和值
    }


    public void deleteZNode(String path) throws Exception {
        curatorFramework
                .delete()
                .deletingChildrenIfNeeded()//如果有子节点,会先自动删除子节点再删除本结点
                .forPath(path);
    }

    public String getZNodeData(String path) throws Exception {
        byte[] dataBytes = curatorFramework.getData().forPath(path);
        return new String(dataBytes, Charset.defaultCharset());
    }

    public void setValue(String path,String value) throws Exception {
        Stat stat = curatorFramework.checkExists().forPath(path);
        if (stat==null){
            System.out.println("ZNode does not exists");
        }else {
            curatorFramework
                    .setData()
                    .withVersion(stat.getVersion())
                    .forPath(path,value.getBytes());
        }
    }

    public void addWatcherWithTreeCache(String path) throws Exception {
        TreeCache treeCache=new TreeCache(curatorFramework,path);
        TreeCacheListener treeCacheListene=new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                if(treeCacheEvent.getType()==TreeCacheEvent.Type.NODE_REMOVED){
                    while (treeCache.iterator().hasNext()){

                        System.out.println("事件路径:"+ treeCache.iterator().next().getPath()+"发生数据变化,新数据为"+new String(treeCache.iterator().next().getData()));

                    }
                }
            }
        };
        treeCache.getListenable().addListener(treeCacheListene);
        treeCache.start();

    }

    public void addWatchWithCuratorCache(String path) throws Exception{
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, path);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forInitialized(() -> {
                    System.out.println("-----------初始化完成");
                })
                .forCreates(e -> {
                    System.out.println(e.getPath() + "--------------创建了节点,value = " +new String(e.getData()));
                })
//                .forChanges((oldData, newData) -> {
//                    System.out.println("------------------老节点数据 = " + oldData +"-----新节点数据 = " + newData);
//                })
                .forDeletes(e -> {
                    System.out.println( "-----------------删除了节点" + e);
                })
                .build();

        curatorCache.listenable().addListener(listener);
        curatorCache.start();

    }


    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }
}
