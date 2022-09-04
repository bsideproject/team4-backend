package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class FindUserByUserIdResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Email
    private String email;

    private String mainPetId;

    private User.Role role;

    private Long familyId;

    @URL
    private String userImageUrl;

}
