package com.sophon.schedule.remote.thrift.service;

import org.apache.thrift.TException;

/**
 * TODO
 * thrift -gen java xx.thrift
 * @Author jinmu
 * @Date 2023/11/3 14:21
 */
public class GreetingServiceImpl implements GreetingService.Iface {

    @Override
    public String sayHello(String name) throws TException {
        return "hello, " + name;
    }
}
