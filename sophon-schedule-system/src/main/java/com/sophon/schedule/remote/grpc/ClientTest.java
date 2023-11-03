package com.sophon.schedule.remote.grpc;

import com.google.protobuf.Any;
import com.google.protobuf.UnsafeByteOperations;
import com.sophon.schedule.rpc.GreeterGrpc;
import com.sophon.schedule.rpc.HelloReply;
import com.sophon.schedule.rpc.HelloRequest;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 11:25
 */
public class ClientTest {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 50051;
        ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
        RequestGrpc.RequestBlockingStub blockingStub = RequestGrpc.newBlockingStub(managedChannel);


        byte[] jsonBytes = "我是客户端，你好".getBytes(StandardCharsets.UTF_8);
        Metadata.Builder metaBuilder = Metadata.newBuilder().setType("test");
        Payload payload = Payload.newBuilder()
                .setBody(Any.newBuilder().setValue(UnsafeByteOperations.unsafeWrap(jsonBytes)))
                .setMetadata(metaBuilder.build()).build();


        Payload response = blockingStub.request(payload);
        System.out.println("接收到来自服务端的回信 ----- " + response.getBody().getValue().toStringUtf8());
    }
}
