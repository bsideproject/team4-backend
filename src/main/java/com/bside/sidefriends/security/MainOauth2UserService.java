package com.bside.sidefriends.security;

import com.bside.sidefriends.security.provider.GoogleUserInfo;
import com.bside.sidefriends.security.provider.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class MainOauth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("userRequest info : " + userRequest.getClientRegistration());
        System.out.println("oAuth2 user : " + oAuth2User);

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("Google OAuth Login Request");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();

        System.out.println("username : " + username);
        System.out.println("email : " + email);
        System.out.println("role : " + role);
        System.out.println("provider : " + provider);
        System.out.println("providerId : " + providerId);

        //TODO : user가 존재하면 신규 가입

        return new mainOAuth2User(oAuth2User.getAttributes());
    }

}
