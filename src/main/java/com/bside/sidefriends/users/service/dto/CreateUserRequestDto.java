package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Builder
    public CreateUserRequestDto(String name, String email, String username, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.provider = provider;
        this.providerId = providerId;
    }
}