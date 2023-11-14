package com.sophon.schedule.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
