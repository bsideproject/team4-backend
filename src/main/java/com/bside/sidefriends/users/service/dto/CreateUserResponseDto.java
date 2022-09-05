package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;


@AllArgsConstructor
@Getter
public class CreateUserResponseDto {

    private Long userId;

    private String name;

    @Email
    private String email;

    private User.Role role;

    // FIXME: Long 타입 변경 필요. IR.
    private String mainPetId;

    @URL
    private String userImageUrl;

}
