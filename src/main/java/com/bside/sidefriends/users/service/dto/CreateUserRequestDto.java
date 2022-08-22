package com.bside.sidefriends.users.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequestDto {

    @NotNull
    String name;

    @NotNull
    String email;

    @NotNull
    String provider;

    @NotNull
    String providerId;

    @Builder
    public CreateUserRequestDto(String name, String email, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}