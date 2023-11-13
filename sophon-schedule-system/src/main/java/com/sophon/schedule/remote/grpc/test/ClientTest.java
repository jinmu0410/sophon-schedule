package com.sophon.schedule.remote.grpc.test;

import com.sophon.schedule.remote.grpc.Payload;
import com.sophon.schedule.remote.grpc.RequestGrpc;
import com.sophon.schedule.remote.grpc.utils.PayloadConvert;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;


/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 15:06
 */
public class ClientTest {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 50051;
        ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
        RequestGrpc.RequestBlockingStub blockingStub = RequestGrpc.newBlockingStub(managedChannel);

        TestRequest testRequest = new TestRequest();
        testRequest.setRequestId("request-1");
        testRequest.setRequestText("测试请求数据");

        Payload payload = PayloadConvert.convert(testRequest, "127.0.0.1", "");

        Payload response = blockingStub.request(payload);
        //todo 客户端和服务端都要加载PayloadRegistry
        Object parse = PayloadConvert.parse(response);
        if(parse instanceof TestResponse){
            TestResponse testResponse = (TestResponse) parse;
            System.out.println("接收到来自服务端的回信 ----- , responseId = " + testResponse.getResponseId() + " responseText = " + testResponse.getResponseText());
        }

    }
}
