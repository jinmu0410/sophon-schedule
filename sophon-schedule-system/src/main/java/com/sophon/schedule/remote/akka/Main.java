package com.sophon.schedule.remote.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.serialization.Serialization;
import com.sophon.schedule.remote.akka.client.ClientActor;
import com.sophon.schedule.remote.akka.server.ServerActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static final String AKKA_REMOTE_NETTY_TCP_HOSTNAME = "akka.remote.netty.tcp.hostname";
    public static ActorSystem actorSystem;

    public static void main(String[] args) throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        Config config = ConfigFactory.parseString(AKKA_REMOTE_NETTY_TCP_HOSTNAME + "=" + hostname);
        actorSystem = ActorSystem.create("jinmu", config.withFallback(ConfigFactory.load()));

        //server 注意
        ActorRef server = actorSystem.actorOf(Props.create(ServerActor.class), getActorRefName(ServerActor.class));
        System.out.println("server path = " + Serialization.serializedActorPath(server));
        //client
        ActorRef client = actorSystem.actorOf(Props.create(ClientActor.class), getActorRefName(ClientActor.class));
        System.out.println("client path = " + Serialization.serializedActorPath(client));

        /**
         * tell 异步发送
         *
         * 第一个参数为消息，它可以是任何可序列化的数据或对象，第二个参数表示发送者，一般是另外一个Actor的引用，ActorRef.noSender（）表示无发送者
         */
        //给client发送消息，没有发送者
        //client.tell("哈喽",ActorRef.noSender());

        //客户端给服务端发消息
        //server.tell("哈喽!!!",client);


        /**
         * 第二种发送消息的方式
         */
        ActorSelection clientSelection = actorSystem.actorSelection(Serialization.serializedActorPath(client));
        clientSelection.tell("测试第二种消息发送方式",ActorRef.noSender());

        actorSystem.terminate();
    }


    public static String getActorRefName(Class clazz) {
        return clazz.getSimpleName();
    }
}
