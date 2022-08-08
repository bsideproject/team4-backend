package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;

import javax.validation.constraints.NotNull;

public class ModifyUserRoleRequestDto {

    @NotNull
    private User.Role role;
}
