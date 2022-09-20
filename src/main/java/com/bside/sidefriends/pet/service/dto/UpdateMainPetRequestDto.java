package com.bside.sidefriends.pet.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value = "회원 대표 반려동물 설정")
public class UpdateMainPetRequestDto {

    @NotNull
    @ApiModelProperty(value = "회원 대표 반려동물 id", example = "1", required = true)
    private Long mainPetId;
}
