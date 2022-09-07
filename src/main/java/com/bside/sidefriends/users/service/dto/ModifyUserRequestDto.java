package com.bside.sidefriends.users.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ModifyUserRequestDto {

    @NotNull
    private String name;

}
