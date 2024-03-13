package com.sophon.schedule.api.ds;

import com.github.weaksloth.dolphins.core.DolphinClient;
import com.github.weaksloth.dolphins.datasource.DataSourceCreateParam;
import com.github.weaksloth.dolphins.datasource.DataSourceQueryResp;

import java.util.List;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/2 13:16
 */
public class DataSourceTest {

    public static void main(String[] args) {
        BeanConfig beanConfig = new BeanConfig();
        DolphinClient client = beanConfig.dolphinClient();

        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setHost("47.113.150.189");
        dataSourceCreateParam.setPort("3306");
        dataSourceCreateParam.setName("测试数据源");
        dataSourceCreateParam.setDatabase("shortv");
        dataSourceCreateParam.setUserName("shortv");
        dataSourceCreateParam.setPassword("H#m8@N7s$yMh233");
        dataSourceCreateParam.setType("MYSQL");


        //Boolean aBoolean = client.opsForDataSource().create(dataSourceCreateParam);
        //System.out.println(aBoolean);

        List<DataSourceQueryResp> dataSourceQueryRespList = client.opsForDataSource().list("");
        System.out.println(dataSourceQueryRespList);
    }
}
