package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class FindUserByUserIdResponseDto {

    @NotNull
    Long userId;

    @NotNull
    String name;

    String nickname;

    @Email
    String email;

    String mainPetId;

    User.Role role;

}
