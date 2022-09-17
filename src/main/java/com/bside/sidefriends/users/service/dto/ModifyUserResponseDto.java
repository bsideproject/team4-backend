package com.bside.sidefriends.users.service.dto;

import com.bside.sidefriends.users.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ModifyUserResponseDto {

    @ApiModelProperty(value = "회원 id", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "회원 이름", required = true)
    private String name;

    @ApiModelProperty(value = "회원 대표 반려동물 id")
    private Long mainPetId;

    @ApiModelProperty(value = "회원 권한")
    private User.Role role;

    @Email
    @ApiModelProperty(value = "회원 이메일")
    private String email;

    @URL
    @ApiModelProperty(value = "회원 프로필 이미지 url")
    private String userImageUrl;

}
