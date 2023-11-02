package com.sophon.schedule.register;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 14:29
 */
@Data
@AllArgsConstructor
public class Event {

    private String type;

    private DataChange newData;

    private DataChange oldData;

    @AllArgsConstructor
    @Data
    public static class DataChange{

        private String path;

        private byte[] data;

    }
}
