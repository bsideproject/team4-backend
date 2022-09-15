package com.bside.sidefriends.pet.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class DeletePetResponseDto {

    @NotNull
    @ApiModelProperty(value = "반려동물 id", example = "1", required = true)
    private Long petId;

    @NotNull
    @ApiModelProperty(value = "반려동물 삭제 여부", required = true)
    private boolean isDeleted;
}
