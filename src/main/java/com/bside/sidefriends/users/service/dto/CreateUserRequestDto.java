package com.bside.sidefriends.users.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserRequestDto {

    @NotNull
    String name;

    @NotNull
    String email;

    @NotNull
    String username;

    @NotNull
    String provider;

    @NotNull
    String providerId;

}
