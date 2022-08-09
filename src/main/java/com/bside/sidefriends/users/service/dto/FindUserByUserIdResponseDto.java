package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class FindUserByUserIdResponseDto {

    @NotNull
    Long id;

    @NotNull
    String name;

    @Email
    String email;

    String mainPetId;

    User.Role role;

}
