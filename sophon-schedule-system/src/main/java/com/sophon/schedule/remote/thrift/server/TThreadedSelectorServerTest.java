package com.sophon.schedule.remote.thrift.server;

import com.sophon.schedule.remote.thrift.service.GreetingService;
import com.sophon.schedule.remote.thrift.service.GreetingServiceImpl;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 17:48
 */
public class TThreadedSelectorServerTest {

    public static void main(String[] args) {
        try {
            GreetingService.Processor<GreetingService.Iface> processor = new GreetingService.Processor<>(new GreetingServiceImpl());

            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(9123);
            TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(serverTransport)
                    .processor(processor)
                    .transportFactory(new TFramedTransport.Factory())
                    .protocolFactory(new TCompactProtocol.Factory());

            TThreadedSelectorServer server = new TThreadedSelectorServer(serverArgs);

            System.out.println("Starting the server...");
            server.serve();

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
