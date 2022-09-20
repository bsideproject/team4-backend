package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value = "가족 그룹 추가")
public class CreateFamilyRequestDto {

    @NotNull
    @ApiModelProperty(value = "가족 그룹장 회원 id", example = "1")
    private Long groupManagerId;

}
