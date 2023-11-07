package com.sophon.schedule.remote.grpc.test;

import com.sophon.schedule.remote.grpc.core.Response;
import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 15:24
 */
@Data
public class TestResponse extends Response {

    private String responseText;
}
