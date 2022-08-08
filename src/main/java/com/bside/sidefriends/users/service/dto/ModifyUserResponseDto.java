package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ModifyUserResponseDto {

    private Long id;

    @NotNull
    private String name;

    @Email
    private String email;

}
