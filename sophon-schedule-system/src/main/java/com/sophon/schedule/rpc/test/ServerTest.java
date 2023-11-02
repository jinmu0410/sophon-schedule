package com.sophon.schedule.rpc.test;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;

import java.io.IOException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 20:50
 */
public class ServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        int port = 50051;
        Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new GreeterImpl())
                .build()
                .start();

        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
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
