package com.bside.sidefriends.security;

import com.bside.sidefriends.security.provider.GoogleUserInfo;
import com.bside.sidefriends.security.provider.KakaoUserInfo;
import com.bside.sidefriends.security.provider.OAuth2UserInfo;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.UserServiceImpl;
import com.bside.sidefriends.users.service.dto.UserCreateRequestDto;
import com.bside.sidefriends.users.service.dto.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final UserServiceImpl userService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("MainOauth2UserService userRequest info : " + userRequest.getClientRegistration());
        System.out.println("MainOauth2UserService oAuth2 user : " + oAuth2User);
        System.out.println("MainOauth2UserService oAuth2 user attributes: " + oAuth2User.getAttributes());

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
            // TODO-jh : profile도 저장해야 한다
            UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                    .name(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .isFamilyLeader(false)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            UserCreateResponseDto userResponseDto = userService.createUser(userCreateRequestDto);

            user = userRepository.findById(userResponseDto.getId()).get();

        }

        return new mainOAuth2User(user, oAuth2User.getAttributes());
    }


}
