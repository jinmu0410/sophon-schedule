package com.sophon.schedule.master.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/8/9 20:05
 */
public class WatchListenerTest {

    public CuratorFramework curatorFramework;

    public WatchListenerTest(CuratorFramework curatorFramework){

        this.curatorFramework = curatorFramework;
    }


    public void addWatchWithCuratorCache(String path) throws Exception{
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, path);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forInitialized(() -> {
                    System.out.println("初始化完成");
                })
                .forCreates(e -> {
                    System.out.println(e.getPath() + "创建了节点,value = " +new String(e.getData()));
                })
                .forChanges((oldData, newData) -> {
                    System.out.println("老节点数据 = " + oldData +"-----新节点数据 = " + newData);
                })
                .forDeletes(e -> {
                    System.out.println( "删除了节点" + e);
                })
                .build();

        curatorCache.listenable().addListener(listener);
        curatorCache.start();

    }

}

