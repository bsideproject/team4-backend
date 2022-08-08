package com.bside.sidefriends.security;

import com.bside.sidefriends.security.provider.GoogleUserInfo;
import com.bside.sidefriends.security.provider.KakaoUserInfo;
import com.bside.sidefriends.security.provider.OAuth2UserInfo;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MainOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("MainOauth2UserService userRequest info : " + userRequest.getClientRegistration());
        System.out.println("MainOauth2UserService oAuth2 user : " + oAuth2User);

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("Google OAuth Login Request");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("Kakao OAuth Login Request");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("Requested OAuth Login is not supported");
        }

        Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        } else {
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role(User.Role.ROLE_USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new mainOAuth2User(user, oAuth2User.getAttributes());
    }


}
