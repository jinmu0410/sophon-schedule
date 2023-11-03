package com.sophon.schedule.remote.grpc.adapter;

import com.google.protobuf.Any;
import com.google.protobuf.UnsafeByteOperations;
import com.sophon.schedule.remote.grpc.Metadata;
import com.sophon.schedule.remote.grpc.Payload;
import com.sophon.schedule.remote.grpc.RequestGrpc;
import io.grpc.stub.StreamObserver;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 * 服务端处理客户端请求
 * @Author jinmu
 * @Date 2023/11/3 10:48
 */
public class GRpcRequestAdapter extends RequestGrpc.RequestImplBase {

    @Override
    public void request(Payload request, StreamObserver<Payload> responseObserver) {

        //todo 根据metaData的type判断来的是什么类型数据
        System.out.println("type = "  + request.getMetadata().getType());
        System.out.println("body = " + request.getBody().getValue().toStringUtf8());


        byte[] jsonBytes = "我是服务端，哈哈哈".getBytes(StandardCharsets.UTF_8);
        Metadata.Builder metaBuilder = Metadata.newBuilder().setType("test");

        Payload payload = Payload.newBuilder()
                .setBody(Any.newBuilder().setValue(UnsafeByteOperations.unsafeWrap(jsonBytes)))
                .setMetadata(metaBuilder.build()).build();

        responseObserver.onNext(payload);
        responseObserver.onCompleted();
    }
}
