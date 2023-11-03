package com.sophon.schedule.remote.thrift.server;

import com.sophon.schedule.remote.thrift.service.GreetingService;
import com.sophon.schedule.remote.thrift.service.GreetingServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 14:26
 */
public class ServerTest {

    public static void main(String[] args) {
        try {
            TServerSocket serverTransport = new TServerSocket(9123);

            GreetingService.Processor<GreetingService.Iface> processor = new GreetingService.Processor<>(new GreetingServiceImpl());

            // 构建服务器
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the thrift server ...");
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
