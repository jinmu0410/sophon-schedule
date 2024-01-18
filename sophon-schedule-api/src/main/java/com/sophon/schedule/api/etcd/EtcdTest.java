package com.sophon.schedule.api.etcd;

import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/12/6 15:47
 */
public class EtcdTest {

    private static Client client;


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        init();
        if(client != null){
            System.out.println("连接成功");
        }
        Object name = get("name");
        System.out.println(name);
    }

    public static void init(){
        client = Client.builder()
                //这里的etcd服务列表可以写多个,用逗号分隔
                .endpoints("http://39.105.113.25:2379".split(","))
                .build();
    }

    public static void put(String key,String value) throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();
        CompletableFuture<PutResponse> completableFuture = kvClient.put(
                ByteSequence.from(key, StandardCharsets.UTF_8),
                ByteSequence.from(value, StandardCharsets.UTF_8));

        if(completableFuture.get().getHeader() != null){
            System.out.println(completableFuture.get().getHeader().toString());
        }
        client.close();
    }

    public static Object get(String key) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println(111);
        KV kvClient = client.getKVClient();
        System.out.println(222);
        CompletableFuture<GetResponse> completableFuture = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8));

        String getBackValue = completableFuture.get().getKvs().get(0).getValue().toString(StandardCharsets.UTF_8);

        System.out.println(3333);
        client.close();
        return getBackValue;
    }
}
