package com.bside.sidefriends.security.provider;

import java.util.Map;

public class KakaoUserInfo<T> implements OAuth2UserInfo {

    /**
     * 카카오 oauth 로그인 제공 정보
     * 1. ClientRegistration
     * {
     *      registrationId='kakao',
     *      clientId='xx',
     *      clientSecret='mWoxtEkCorSQZfL2geko0qjRMEGPcPvPxx',
     *      clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@2590a0,
     *      authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3,
     *      redirectUri='http://localhost:8080/login/oauth2/code/kakao',
     *      scopes=[profile_nickname, account_email],
     *      providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@438f272f,
     *      clientName='kakao'
     * }
     *
     * 2. UserRequest.getAttributes()
     * {
     *      id=2369946793,
     *      connected_at=2022-08-01T10:22:45Z,
     *      properties={
     *          nickname=송이레
     *      },
     *      kakao_account={
     *          profile_nickname_needs_agreement=false,
     *          profile={
     *              nickname=송이레
     *          },
     *          has_email=true,
     *          email_needs_agreement=false,
     *          is_email_valid=true,
     *          is_email_verified=true,
     *          email=sirzzang@naver.com
     *      }
     * }
     */

    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributesAccount.get("email");
    }

    @Override
    public String getName() {
        return (String) attributesProfile.get("nickname");
    }

    @Override
    public String getImageUrl() {
        return "";
    }
}
