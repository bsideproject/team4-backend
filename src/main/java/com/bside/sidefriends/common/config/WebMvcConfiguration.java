package com.bside.sidefriends.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // images로 오는 요청에 대한 정적 파일 서빙 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/")
                .setCachePeriod(60 * 60 * 24 * 365);

    }
}