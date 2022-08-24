package com.bside.sidefriends.common.config;

import com.bside.sidefriends.common.util.ImageHandler;
import com.bside.sidefriends.users.service.util.LocalImageHandler;
import com.bside.sidefriends.users.service.util.NginxImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SideFriendsConfiguration {

    @Bean
    public ImageHandler userImageHandler() {
//        return new LocalImageHandler();
        return new NginxImageHandler();
    }

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }
}
