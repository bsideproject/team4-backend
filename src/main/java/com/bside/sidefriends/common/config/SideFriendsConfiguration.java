package com.bside.sidefriends.common.config;

import com.bside.sidefriends.common.util.ImageHandler;
import com.bside.sidefriends.users.service.util.LocalImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SideFriendsConfiguration {

    @Bean
    public ImageHandler userImageHandler() {
        return new LocalImageHandler();
    }
}
