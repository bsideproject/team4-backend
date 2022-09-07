package com.bside.sidefriends.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiFunction;

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

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // Bearer%20 처리
        if (header.startsWith("Bearer%20")) {
            header = header.replace("Bearer%20", JwtProperties.TOKEN_PREFIX);
        }
        String token = header.replace(JwtProperties.TOKEN_PREFIX,"");

        String userName = "";
        try {
            userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build().verify(token).getSubject();
        } catch (TokenExpiredException e) {
            response = setResponseDto.apply(response, ResponseCode.AUTH_TOKEN_EXPIRED);
            return;
        }  catch (JWTDecodeException e) {
            response = setResponseDto.apply(response, ResponseCode.AUTH_DECODE_FAIL);
            return;
        } catch (JWTVerificationException e) {
            response = setResponseDto.apply(response, ResponseCode.AUTH_VERIFICATION_FAIL);
            return;
        }  catch (Exception e) {
            response = setResponseDto.apply(response, ResponseCode.AUTH_FAIL);
            return;
        }

        Authentication authentication;
        if (userName != null) {

            // FIXME: soft delete 레코드 메서드 구현 여부 변경에 따라, 서비스 시 회원 조회 맥락 고려 후 필요시 아래 메서드 변경 필요. IR.
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

    private BiFunction<HttpServletResponse, ResponseCode, HttpServletResponse> setResponseDto =
            (response, responseCode) ->  {
                ResponseDto<?> responseDto = ResponseDto.onFailWithoutData(responseCode);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json; charset=utf8");
                try {
                    response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return response;
            };

}
