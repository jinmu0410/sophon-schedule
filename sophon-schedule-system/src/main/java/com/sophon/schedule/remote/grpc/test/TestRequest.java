package com.sophon.schedule.remote.grpc.test;

import com.sophon.schedule.remote.grpc.core.Request;
import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 15:21
 */
@Data
public class TestRequest extends Request {

    private String requestText;
}
