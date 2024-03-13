package com.sophon.schedule.api.ds;

import com.github.weaksloth.dolphins.core.DolphinClient;
import com.github.weaksloth.dolphins.task.DataxTask;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/2 13:13
 */
public class DataXTaskTest {

    public static void main(String[] args) {
        BeanConfig beanConfig = new BeanConfig();
        DolphinClient client = beanConfig.dolphinClient();

        Long projectCode = 12459023726016l;

        Long taskCode = client.opsForProcess().generateTaskCode(projectCode, 1).get(0);

        DataxTask dataxTask = new DataxTask();


    }
}
