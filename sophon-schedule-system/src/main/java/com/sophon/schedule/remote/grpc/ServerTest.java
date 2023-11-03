package com.sophon.schedule.remote.grpc;

import com.sophon.schedule.remote.grpc.adapter.GRpcRequestAdapter;
import com.sophon.schedule.rpc.test.GreeterImpl;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;

import java.io.IOException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 11:24
 */
public class ServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new GRpcRequestAdapter())
                .build()
                .start();

        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                server.shutdown();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }
}
