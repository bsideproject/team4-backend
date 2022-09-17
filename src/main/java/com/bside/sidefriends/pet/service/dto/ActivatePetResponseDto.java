package com.bside.sidefriends.pet.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivatePetResponseDto {

    @ApiModelProperty(value = "반려동물 id", example = "1")
    private Long petId;

    @ApiModelProperty(value = "반려동물 기록 활성화 여부", example = "false")
    private boolean isDeactivated;
}
