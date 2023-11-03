package com.sophon.schedule.remote.thrift.client;

import com.sophon.schedule.remote.thrift.service.GreetingService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import java.util.UUID;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 17:52
 */
public class TThreadedSelectorClientTest {

    public static void main(String[] args) {
        try {
            TSocket transport = new TSocket("localhost", 9123);
            transport.open();

            TCompactProtocol protocol = new TCompactProtocol(new TFramedTransport(transport));
            GreetingService.Client client = new GreetingService.Client(protocol);

            String result = client.sayHello("World"+ UUID.randomUUID());
            System.out.println("Server response: " + result);
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
