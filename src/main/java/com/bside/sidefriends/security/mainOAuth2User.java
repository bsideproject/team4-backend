package com.bside.sidefriends.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class mainOAuth2User implements OAuth2User {

    private Map<String, Object> attributes;
    public mainOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO
        return null;
    }

    @Override
    public String getName() {
        //TODO
        return null;
    }
}
