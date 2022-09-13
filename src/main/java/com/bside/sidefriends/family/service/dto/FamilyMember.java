package com.bside.sidefriends.family.service.dto;

import com.bside.sidefriends.users.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(value = "가족 그룹 구성원 정보")
public class FamilyMember {

    @ApiModelProperty(value = "가족 그룹 구성원 회원 id")
    private Long userId;

    @ApiModelProperty(value = "가족 그룹 구성원 회원 이름")
    private String name;

    @ApiModelProperty(value = "가족 그룹 구성원 회원 권한", example = "ROLE_MANAGER")
    private User.Role role;

    @ApiModelProperty(value = "가족 그룹 구성원 회원 프로필 이미지 url")
    private String userImageUrl;

}
