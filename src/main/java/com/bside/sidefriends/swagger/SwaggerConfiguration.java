package com.bside.sidefriends.swagger;

import com.bside.sidefriends.security.auth.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String API_NAME = "SideFriends Project API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "SideFriends 프로젝트 명세서";

    @Bean
    public Docket api() {

        // Parameter #1. Authorization Header
        RequestParameterBuilder authorizationHeader = new RequestParameterBuilder();
        authorizationHeader.name("Authorization")
                .description("Access Token")
                .in(ParameterType.HEADER)
                .required(false)
                .build();
        List<RequestParameter> parameterList = new ArrayList<>();
        parameterList.add(authorizationHeader.build());

        // Docket
        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(parameterList)
                .ignoredParameterTypes(LoginUser.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact("side friends", "https://haru-pet.com/", "sidefriends.devs@gmail.com"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer + accessToken", "Authorization", "header");
    }

}
