package com.sophon.schedule.remote.grpc.utils;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.UnsafeByteOperations;
import com.sophon.schedule.remote.grpc.Metadata;
import com.sophon.schedule.remote.grpc.Payload;
import io.netty.util.internal.StringUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/3 11:04
 */
public class PayloadUtils {

    public static Payload convert(Metadata metadata,String body){
        byte[] jsonBytes = body.getBytes(StandardCharsets.UTF_8);
        Metadata.Builder metaBuilder = Metadata.newBuilder().setType(metadata.getType());

        return Payload.newBuilder()
                .setBody(Any.newBuilder().setValue(UnsafeByteOperations.unsafeWrap(jsonBytes)))
                .setMetadata(metaBuilder.build()).build();
    }

}
