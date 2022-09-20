package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetShareScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class SharePetResponseDto {

    @NotNull
    @ApiModelProperty(value = "반려동물 id", example = "1", required = true)
    private Long petId;

    @NotNull
    @ApiModelProperty(value = "반려동물 공유 범위", notes = "공유 펫인 경우 FAMILY", required = true)
    private PetShareScope shareScope;

    @NotNull
    @ApiModelProperty(value = "반려동물 공유 가족 id", example = "1", required = true)
    private Long familyId;

}
