package com.sophon.schedule.remote.grpc.adapter;


import com.sophon.schedule.remote.grpc.Payload;
import com.sophon.schedule.remote.grpc.RequestGrpc;
import com.sophon.schedule.remote.grpc.test.TestRequest;
import com.sophon.schedule.remote.grpc.test.TestResponse;
import com.sophon.schedule.remote.grpc.utils.PayloadRegistry;
import com.sophon.schedule.remote.grpc.utils.PayloadConvert;
import io.grpc.stub.StreamObserver;


/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 14:57
 */
public class RemoteRequestAdapter extends RequestGrpc.RequestImplBase {

    public RemoteRequestAdapter(){
        PayloadRegistry.init();
    }

    @Override
    public void request(Payload request, StreamObserver<Payload> responseObserver) {
        Object parse = PayloadConvert.parse(request);
        if(parse instanceof TestRequest){
            System.out.println("-----接收到testRequest请求-----");

            TestRequest testRequest = (TestRequest) parse;
            System.out.println("requestId = " + testRequest.getRequestId() + "---" + testRequest.getRequestText());


            //todo 返回消息
            TestResponse testResponse = new TestResponse();
            testResponse.setResponseId("response-1");
            testResponse.setResponseText("测试testResponse响应");
            Payload payload = PayloadConvert.convert(testResponse,"127.0.0.1","");

            responseObserver.onNext(payload);
        }
        responseObserver.onCompleted();
    }

}
