package com.bside.sidefriends.users.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserRequestDto {

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

}
