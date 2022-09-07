package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ModifyUserResponseDto {

    private Long userId;

    @NotNull
    private String name;

    // FIXME: Long 타입 변경 필요. IR.
    private String mainPetId;

    private User.Role role;

    @Email
    private String email;

    @URL
    private String userImageUrl;

}
