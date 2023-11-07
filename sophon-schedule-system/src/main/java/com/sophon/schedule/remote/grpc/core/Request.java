package com.sophon.schedule.remote.grpc.core;


import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 14:46
 */
@Data
public abstract class Request implements Payload {

    private String requestId;
}
