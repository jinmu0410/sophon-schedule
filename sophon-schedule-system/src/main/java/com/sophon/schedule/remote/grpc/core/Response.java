package com.sophon.schedule.remote.grpc.core;

import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 14:53
 */
@Data
public abstract class Response implements Payload {

    private String responseId;

}
