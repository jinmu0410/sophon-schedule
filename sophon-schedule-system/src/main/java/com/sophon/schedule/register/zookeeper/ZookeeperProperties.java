package com.sophon.schedule.register.zookeeper;

import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 14:47
 */
@Data
public class ZookeeperProperties {

    private String namespace;
    private String connectString;
    private RetryPolicy retryPolicy = new RetryPolicy();
    private String digest;
    private int sessionTimeout = 5000;
    private int connectionTimeout = 5;
    private int blockUntilConnected = 3000;


    @Data
    public static final class RetryPolicy {
        private int baseSleepTime = 1000;
        private int maxRetries = 3;
        private int maxSleep = 3000;
    }
}
