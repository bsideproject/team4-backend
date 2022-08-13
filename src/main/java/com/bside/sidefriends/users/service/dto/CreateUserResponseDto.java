package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CreateUserResponseDto {

    private Long id;
    private String name;
    private String email;
    private boolean isDeleted;
    private User.Role role;

}
