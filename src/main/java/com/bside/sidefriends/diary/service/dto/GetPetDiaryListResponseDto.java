package com.bside.sidefriends.diary.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPetDiaryListResponseDto {

    @ApiModelProperty(value = "한줄일기 개수")
    int totalNum;

    @ApiModelProperty(value = "한줄일기 리스트")
    List<PetDiaryInfo> petDiaryList;
}
