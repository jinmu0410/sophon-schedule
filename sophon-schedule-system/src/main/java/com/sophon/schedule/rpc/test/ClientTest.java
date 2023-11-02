package com.sophon.schedule.rpc.test;

import com.sophon.schedule.rpc.GreeterGrpc;
import com.sophon.schedule.rpc.HelloReply;
import com.sophon.schedule.rpc.HelloRequest;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 20:47
 */
public class ClientTest {
    public static void main(String[] args) throws InterruptedException {

        String host = "localhost";
        int port = 50051;
        ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(managedChannel);

        HelloRequest helloRequest = HelloRequest.newBuilder().setName("你好,我叫客户端").build();
        HelloReply reply = blockingStub.sayHello(helloRequest);
        System.out.println("接收到来自服务端的回信 ----- " + reply.getMessage());

        HelloReply response = blockingStub.sayHelloAgain(helloRequest);
        System.out.println("接收到来自服务端的回信 ----- " + response.getMessage());

        managedChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}
