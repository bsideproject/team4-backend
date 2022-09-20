package com.bside.sidefriends.pet.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindAllPetResponseDto {

    @NotNull
    @ApiModelProperty(value = "회원 id", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "회원 대표 반려동물 id", required = true)
    private Long mainPetId;

    @ApiModelProperty(value = "회원 반려동물 수")
    private int totalPetNumber;

    @ApiModelProperty(value = "회원 반려동물 리스트")
    private List<PetInfo> petList;

}
