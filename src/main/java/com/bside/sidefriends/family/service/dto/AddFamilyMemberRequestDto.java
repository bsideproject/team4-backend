package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value = "가족 그룹 구성원 추가")
public class AddFamilyMemberRequestDto {

    @NotNull
    @ApiModelProperty(value = "그룹장 회원 idval", example = "1")
    private Long groupManagerId;


}
