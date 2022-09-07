package com.bside.sidefriends.security.provider;

// OAuth2의 provider 마다 응답해주는 속성값이 달라 공통으로 만든 Inferface
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getImageUrl();
}
