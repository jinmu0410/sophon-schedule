package com.sophon.schedule.master.zk.register;

import com.sophon.schedule.register.SubscribeListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 17:04
 */
public class MasterSubscribeListener implements SubscribeListener {


    @Override
    public void notify(CuratorCacheListener.Type type, ChildData oldData, ChildData newData) {
        if(type.name().equalsIgnoreCase("NODE_CREATED")){
            System.out.println("创建了" + newData.getPath() + "路径。value = " + new String(newData.getData()));
        } else if (type.name().equalsIgnoreCase("NODE_CHANGED")) {
            System.out.println("更新了" + oldData.getPath() + "路径。更新前value = " + new String(oldData.getData()) +" 更新后value = " + new String(newData.getData()) );
        }else if (type.name().equalsIgnoreCase("NODE_DELETED")){
            System.out.println("删除了" + oldData.getPath());
        }
    }
}
