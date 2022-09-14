package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value="가족 그룹장 변경")
public class ChangeFamilyManagerRequestDto {

    @NotNull
    @ApiModelProperty(value="기존 그룹장 회원 id", example="1")
    private Long prevManagerId;

    @NotNull
    @ApiModelProperty(value="변경할 그룹장 회원 id", example="2")
    private Long nextManagerId;
}
