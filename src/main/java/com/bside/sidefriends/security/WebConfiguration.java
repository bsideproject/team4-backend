package com.bside.sidefriends.security;

import com.bside.sidefriends.security.auth.LoginUserArgumentResolver;
import com.bside.sidefriends.security.auth.LoginUsernameArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final LoginUsernameArgumentResolver loginUsernameArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
        argumentResolvers.add(loginUsernameArgumentResolver);
    }

}