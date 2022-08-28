package com.bside.sidefriends.security;


import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.redirect_url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2SuccessHandler start!");
        System.out.println("Authentication : " + authentication.getPrincipal());

        mainOAuth2User principal = (mainOAuth2User) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        System.out.println("OAuth2SuccessHandler token : " + token);

        String jwtToken = JwtProperties.TOKEN_PREFIX +  token;
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +  token);
        response.sendRedirect(redirectUrl + "?token="+jwtToken); // FE로 Redirect 할 경로
    }

}
