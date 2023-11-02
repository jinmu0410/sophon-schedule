package com.sophon.schedule.register;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 11:38
 */
public interface Register {

    Boolean subscribe(String path,SubscribeListener subscribeListener);
}
