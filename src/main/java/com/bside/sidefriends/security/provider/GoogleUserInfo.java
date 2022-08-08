package com.bside.sidefriends.security.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    /**
     * { sub=114007652679685364644,
     *  name=side friends,
     *  given_name=side,
     *  family_name=friends,
     *  picture=https://lh3.googleusercontent.com/a/AItbvml4Ed5RkDgcCzRTwd_E8Ym_9OCrHchfzCJ-QNNK=s96-c,
     *  email=sidefriends.devs@gmail.com,
     *  email_verified=true,
     *  locale=ko}
    *
    *
    * **/



    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return "google";
    }
}
