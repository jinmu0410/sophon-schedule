package com.sophon.schedule.remote.thrift.client;

import com.sophon.schedule.remote.thrift.service.GreetingService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 14:23
 */
public class ClientTest {

    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("127.0.0.1", 9123);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        GreetingService.Client client = new GreetingService.Client(protocol);

        String content = "这里是客户端";

        String result = client.sayHello(content);
        System.out.println("调用结果：" + result);
        transport.close();
    }

}
