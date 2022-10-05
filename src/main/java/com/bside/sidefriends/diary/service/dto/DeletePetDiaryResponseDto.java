package com.bside.sidefriends.diary.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePetDiaryResponseDto {

    @ApiModelProperty(value = "삭제된 한줄일기 id")
    private Long diaryId;
}
