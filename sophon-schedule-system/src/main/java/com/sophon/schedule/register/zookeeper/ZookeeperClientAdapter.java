package com.sophon.schedule.register.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 14:45
 */
public class ZookeeperClientAdapter {

    private static final ThreadLocal<Map<String, InterProcessMutex>> threadLocalLockMap = new ThreadLocal<>();
    private final CuratorFramework client;


    public ZookeeperClientAdapter(ZookeeperProperties zookeeperProperties) {
        client = CuratorFrameworkFactory
                .builder() // 使用工厂类来建造客户端的实例对象
                .connectString(zookeeperProperties.getConnectString()) // 配置zk服务器IP port
                .sessionTimeoutMs(zookeeperProperties.getSessionTimeout())// 设定会话时间
                .retryPolicy(new ExponentialBackoffRetry(zookeeperProperties.getRetryPolicy().getBaseSleepTime(),
                        zookeeperProperties.getRetryPolicy().getMaxRetries(),
                        zookeeperProperties.getRetryPolicy().getMaxSleep()))//设置及重连策略
                .namespace(zookeeperProperties.getNamespace())//方便管理的命名空间,其实就是一级目录
                .build();//建立管道

        System.setProperty("org.apache.zookeeper.Logger", "ERROR");
        client.start();//开启curator
    }

    public CuratorFramework getClient(){
        return client;
    }


    public void createZNode(String path, String value, Boolean tempNode) throws Exception {
        CreateMode createMode = tempNode ? CreateMode.EPHEMERAL : CreateMode.PERSISTENT;
        client
                .create()
                .creatingParentsIfNeeded()//如果是多级结点,这里声明如果需要,自动创建父节点
                .withMode(createMode)
                .forPath(path, value.getBytes());//声明结点路径和值
    }


    public void deleteZNode(String path) throws Exception {
        client
                .delete()
                .deletingChildrenIfNeeded()//如果有子节点,会先自动删除子节点再删除本结点
                .forPath(path);
    }

    public String getZNodeData(String path) throws Exception {
        byte[] dataBytes = client.getData().forPath(path);
        return new String(dataBytes, Charset.defaultCharset());
    }

    public void setValue(String path, String value) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            System.out.println("ZNode does not exists");
        } else {
            client
                    .setData()
                    .withVersion(stat.getVersion())
                    .forPath(path, value.getBytes());
        }
    }

    public boolean acquireLock(String key) {
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, key);
        try {
            interProcessMutex.acquire();
            if (null == threadLocalLockMap.get()) {
                threadLocalLockMap.set(new HashMap<>(3));
            }
            threadLocalLockMap.get().put(key, interProcessMutex);
            return true;
        } catch (Exception e) {
            try {
                interProcessMutex.release();
                throw new RuntimeException("zookeeper get lock error", e);
            } catch (Exception exception) {
                throw new RuntimeException("zookeeper release lock error", e);
            }
        }
    }

    public boolean releaseLock(String key) {
        if (null == threadLocalLockMap.get().get(key)) {
            return false;
        }
        try {
            threadLocalLockMap.get().get(key).release();
            threadLocalLockMap.get().remove(key);
            if (threadLocalLockMap.get().isEmpty()) {
                threadLocalLockMap.remove();
            }
        } catch (Exception e) {
            throw new RuntimeException("zookeeper release lock error", e);
        }
        return true;
    }

}
