package com.bside.sidefriends.users.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateRequestDto {

    @NotNull
    String name;

    String nickname;
    String phoneNumber;

    @NotNull
    String email;

    @NotNull
    boolean isFamilyLeader;

    String provider;
    String providerId;

    @Builder
    public UserCreateRequestDto(String name, String nickname, String phoneNumber, String email, boolean isFamilyLeader, String provider, String providerId) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isFamilyLeader = isFamilyLeader;
        this.provider = provider;
        this.providerId = providerId;
    }
}
