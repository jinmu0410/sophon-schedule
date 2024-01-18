package com.sophon.schedule.api.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.sophon.schedule.api.hls.MinioUtils;
import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 17:24
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping
    public String hello(){
        log.info("--------test-log-----------");

        return "hello,world";
    }


    @GetMapping("/video")
    public ResponseEntity<InputStreamResource> streamVideo() throws IOException {
        MinioUtils minioUtils = new MinioUtils();
        // 远程视频文件URL
        //String videoFileUrl = "http://192.168.0.242:9000/shortv//security/3e836611147f42b18c89eeebd48a66dc/3e836611147f42b18c89eeebd48a66dc.m3u8?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=0PgdJfYVRke9hZuOpVwj%2F20231213%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231213T101112Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=2d81f54205179f1ec6766a17eca80298c246c4824805fc1b8d4433782eaabd88";

        //String videoFileUrl = minioUtils.getPrivateUrl("2024/01/02/4843dfa839aa430e9bb1bc3e171a104e.mp4",300);


        GetObjectResponse stream = minioUtils.getStream("2024/01/02/", "4843dfa839aa430e9bb1bc3e171a104e.mp4");

        HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=4843dfa839aa430e9bb1bc3e171a104e.mp4");


        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(new InputStreamResource(stream));
    }
}
