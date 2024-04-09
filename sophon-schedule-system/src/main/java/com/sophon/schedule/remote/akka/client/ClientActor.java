package com.sophon.schedule.remote.akka.client;

import akka.actor.UntypedAbstractActor;


public class ClientActor extends UntypedAbstractActor {
    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        String msg = (String) message;
        System.out.println("客户端收到消息: " + msg);
    }
}
