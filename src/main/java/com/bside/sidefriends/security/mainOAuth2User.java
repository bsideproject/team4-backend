package com.bside.sidefriends.security;

import com.bside.sidefriends.users.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class mainOAuth2User implements OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    public mainOAuth2User(User user) {
        this.user = user;
    }

    public mainOAuth2User(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(()->{ return user.getRole().name();});
        return collect;
    }

    @Override
    public String getName() {
        return user.getId().toString(); // User의 Primary Key
    }

    public String getUsername() {
        return user.getUsername();
    }
}
