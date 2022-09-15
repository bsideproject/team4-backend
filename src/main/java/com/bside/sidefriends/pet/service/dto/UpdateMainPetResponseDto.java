package com.bside.sidefriends.pet.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdateMainPetResponseDto {

    @NotNull
    @ApiModelProperty(value = "회원 id", example = "1", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "회원 대표펫 id", example = "1", required = true)
    private Long mainPetId;

    @ApiModelProperty(value = "회원 대표펫 정보")
    private PetInfo petInfo;

}
