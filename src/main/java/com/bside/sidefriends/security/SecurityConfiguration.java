package com.bside.sidefriends.security;

import com.bside.sidefriends.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MainOauth2UserService mainOauth2UserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    // 가족 그룹장 권한이 필요한 API 엔드포인트
    private static final String FAMILY_MANAGER_REQUIRED_ENDPOINTS = "/api/v1/family/{\\d+}/**";
    private static final String PET_SHARE_ENDPOINT = "/api/v1/pet/**/share";

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();

        http
            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), userRepository))
                .authorizeRequests()
                    .antMatchers("/user/**").authenticated()
                    .antMatchers("/manager/**").hasRole("MANAGER")
                    .antMatchers(HttpMethod.DELETE, FAMILY_MANAGER_REQUIRED_ENDPOINTS).hasRole("MANAGER")
                    .antMatchers(HttpMethod.POST, FAMILY_MANAGER_REQUIRED_ENDPOINTS).hasRole("MANAGER")
                    .antMatchers(HttpMethod.PUT, FAMILY_MANAGER_REQUIRED_ENDPOINTS).hasRole("MANAGER")
                    .antMatchers(HttpMethod.POST, PET_SHARE_ENDPOINT).hasRole("MANAGER") // 펫 공유
                .anyRequest().permitAll()
            .and()
                .oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(mainOauth2UserService);
        return http.build();
    }

}
