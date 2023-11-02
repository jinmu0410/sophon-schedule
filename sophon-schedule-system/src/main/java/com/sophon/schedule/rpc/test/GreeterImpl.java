package com.sophon.schedule.rpc.test;

import com.sophon.schedule.rpc.GreeterGrpc;
import com.sophon.schedule.rpc.HelloReply;
import com.sophon.schedule.rpc.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 20:50
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("你好！我是服务端 , sayHello方法 " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void sayHelloAgain(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("你好！我是服务端, sayHelloAgain方法 " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
