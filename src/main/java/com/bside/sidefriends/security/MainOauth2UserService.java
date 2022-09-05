package com.bside.sidefriends.security;

import com.bside.sidefriends.security.provider.GoogleUserInfo;
import com.bside.sidefriends.security.provider.KakaoUserInfo;
import com.bside.sidefriends.security.provider.OAuth2UserInfo;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.domain.UserImage;
import com.bside.sidefriends.users.error.exception.UserAlreadyExistsException;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.UserImageService;
import com.bside.sidefriends.users.service.UserService;
import com.bside.sidefriends.users.service.dto.CreateUserRequestDto;
import com.bside.sidefriends.users.service.dto.CreateUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserImageService userImageService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

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

            if (user.isDeleted()) { // 사용자 정보가 존재하나, 삭제되어 유효하지 않은 경우
                user.restore();
            }

            user.setEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        } else {
            // 신규 회원가입
            CreateUserRequestDto userCreateRequestDto = CreateUserRequestDto.builder()
                    .name(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            CreateUserResponseDto userResponseDto = userService.createUser(userCreateRequestDto);
            user = userRepository.findById(userResponseDto.getUserId()).get();

            // 신규 회원가입 - 프로필 정보 저장
//            if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
//                UserImage userImage = UserImage.builder()
//                        .user(user)
//                        .imageUrl(oAuth2UserInfo.getImageUrl())
//                        .build();
//                userImageService.createUserImage(userImage);
//            }

        }

        return new mainOAuth2User(user, oAuth2User.getAttributes());
    }


}
