package com.sophon.schedule.remote.grpc.utils;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.UnsafeByteOperations;
import com.sophon.schedule.remote.grpc.Payload;
import com.sophon.schedule.remote.grpc.core.Request;
import com.sophon.schedule.remote.grpc.core.Response;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 15:41
 */
public class PayloadUtil {


    public static Payload convert(Request request,String clientIp,String extra) {
        byte[] jsonBytes = JacksonUtils.toJsonBytes(request);

        return Payload.newBuilder()
                .setBody(Any.newBuilder().setValue(UnsafeByteOperations.unsafeWrap(jsonBytes)))
                .setType(request.getClass().getSimpleName())
                .setClientIp(clientIp)
                .setExtra(extra)
                .build();

    }

    public static Payload convert(Response response, String clientIp, String extra) {
        byte[] jsonBytes = JacksonUtils.toJsonBytes(response);

        return Payload.newBuilder()
                .setBody(Any.newBuilder().setValue(UnsafeByteOperations.unsafeWrap(jsonBytes)))
                .setType(response.getClass().getSimpleName())
                .setClientIp(clientIp)
                .setExtra(extra)
                .build();

    }

    public static Object parse(Payload payload){
        PayloadRegistry.init();
        Class classType = PayloadRegistry.getClassByType(payload.getType());
        if (classType != null) {
            ByteString byteString = payload.getBody().getValue();
            ByteBuffer byteBuffer = byteString.asReadOnlyByteBuffer();
            Object obj = JacksonUtils.toObj(new ByteBufferBackedInputStream(byteBuffer), classType);

            return obj;
        } else {
            throw new RuntimeException("反解析payload失败");
        }
    }

}
