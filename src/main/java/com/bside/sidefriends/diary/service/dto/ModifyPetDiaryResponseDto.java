package com.bside.sidefriends.diary.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyPetDiaryResponseDto {

    @ApiModelProperty(value = "펫 id", example = "1")
    private Long petId;

    @ApiModelProperty(value = "한줄일기 정보")
    private PetDiaryInfo petDiaryInfo;

}
