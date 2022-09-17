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
public class FindUserByUserIdResponseDto {

    @NotNull
    @ApiModelProperty(value = "회원 id")
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "회원 이름")
    private String name;

    @Email
    @ApiModelProperty(value = "회원 이메일")
    private String email;

    @ApiModelProperty(value = "회원 대표 반려동물 id")
    private Long mainPetId;

    @ApiModelProperty(value = "회원 권한")
    private User.Role role;

    @ApiModelProperty(value = "회원 가족 그룹 id")
    private Long familyId;

    @URL
    @ApiModelProperty(value = "회원 프로필 이미지 url")
    private String userImageUrl;

}
