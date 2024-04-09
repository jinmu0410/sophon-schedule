package com.sophon.schedule.remote.akka.server;

import akka.actor.UntypedAbstractActor;

public class ServerActor extends UntypedAbstractActor {

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        String msg = (String) message;
        System.out.println("服务端收到消息: " + msg);
        getSender().tell("你好，我是服务端",getSelf());
    }
}
