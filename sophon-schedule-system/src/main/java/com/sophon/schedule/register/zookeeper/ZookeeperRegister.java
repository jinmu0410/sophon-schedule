package com.sophon.schedule.register.zookeeper;

import com.sophon.schedule.register.Register;
import com.sophon.schedule.register.SubscribeListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 11:49
 */
public class ZookeeperRegister implements Register {

    private final CuratorFramework client;


    public ZookeeperRegister(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public Boolean subscribe(String path, SubscribeListener subscribeListener) {
        CuratorCache curatorCache = CuratorCache.build(client, path);
        try {
            curatorCache.listenable().addListener(new CuratorCacheListener() {
                @Override
                public void event(Type type, ChildData oldData, ChildData newData) {
                    subscribeListener.notify(type,oldData,newData);
                }
            });

            curatorCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
