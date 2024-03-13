package com.sophon.schedule.api.ds;

import com.github.weaksloth.dolphins.core.DolphinClient;
import com.github.weaksloth.dolphins.remote.DolphinsRestTemplate;
import com.github.weaksloth.dolphins.remote.request.DefaultHttpClientRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.RequestContent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/1 13:42
 */
@Configuration
public class BeanConfig {

    @Bean
    public DolphinClient dolphinClient() {
        String token = "d2ee0f357111c23d97425fd928439abd";	// dolphin scheduler token
        String dolphinAddress = "http://192.168.217.232:12345/dolphinscheduler";  // dolphin scheduler address
        DolphinsRestTemplate restTemplate =
                new DolphinsRestTemplate(
                        new DefaultHttpClientRequest(
                                HttpClients.custom()
                                        .addInterceptorLast(new RequestContent(true))
                                        .setDefaultRequestConfig(RequestConfig.custom().build())
                                        .build(),
                                RequestConfig.custom().build()));

        return new DolphinClient(token,dolphinAddress,restTemplate);
    }

}
