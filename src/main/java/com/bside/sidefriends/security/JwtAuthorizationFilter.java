package com.bside.sidefriends.security;

import com.auth0.jwt.JWT;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println("JwtAuthorizationFilter start : " + header);

//        // Bearer%20 처리
//        if (header.startsWith("Bearer%20")) {
//            header = header.replace("Bearer%20", JwtProperties.TOKEN_PREFIX);
//        }

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(JwtProperties.TOKEN_PREFIX,"");
        String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build().verify(token).getSubject(); //TODO : 유효하지 않은 Token이 들어오면 SignatureVerificationException이 발생한다
        // TODO-jh : 유효기간이 만료한 Token이 들어오면 com.auth0.jwt.exceptions.TokenExpiredException: The Token has expired on 2022-08-08T11:13:57Z 발생

        Authentication authentication;
        if (userName != null) {
            User user = userRepository.findByUsername(userName).orElseThrow(); //TODO : findByUsername한 값이 없을 때 처리할 Exception 구현해야 한다
            mainOAuth2User oAuth2User = new mainOAuth2User(user);

            UsernamePasswordAuthenticationToken auth
                    = new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities());
            authentication = auth;
        } else {
            authentication = null;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
