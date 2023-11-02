package com.sophon.schedule.register;


import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 11:40
 */
public interface SubscribeListener {

    void notify(CuratorCacheListener.Type type, ChildData oldData, ChildData newData);
}
