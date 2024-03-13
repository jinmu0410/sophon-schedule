package com.sophon.schedule.api.ds;

import com.github.weaksloth.dolphins.core.DolphinClient;
import com.github.weaksloth.dolphins.project.ProjectCreateParam;
import com.github.weaksloth.dolphins.project.ProjectInfoResp;
import com.github.weaksloth.dolphins.remote.DolphinsRestTemplate;
import com.github.weaksloth.dolphins.remote.request.DefaultHttpClientRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.RequestContent;

import java.util.List;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/1 13:44
 */
public class ProjectTest {

    public static void main(String[] args) {
        BeanConfig beanConfig = new BeanConfig();


        ProjectCreateParam projectCreateParam = new ProjectCreateParam();
        projectCreateParam.setProjectName("jinmu-test");
        projectCreateParam.setDescription("测试sdk使用");

        //创建项目
        //ProjectInfoResp resp = beanConfig.dolphinClient().opsForProject().create(projectCreateParam);


        //查询project /  12459023726016
        List<ProjectInfoResp> projectInfoList = beanConfig.dolphinClient().opsForProject().page(1, 10, "123456");
        System.out.println(projectInfoList);

    }
}
