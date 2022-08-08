package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class ModifyUserResponseDto {

    private Long id;

    @NotNull
    private String name;

    @Email
    private String email;

}
