package com.bside.sidefriends.diary.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ApiModel(value = "펫 한줄일기 수정")
public class ModifyPetDiaryRequestDto {

    @NotNull
    @Size(min = 1, message = "한 줄 일기 내용은 1자 이상이어야 합니다.")
    @Size(max = 140, message = "한 줄 일기 내용은 140자 이하여야 합니다.")
    @ApiModelProperty(value = "내용", example = "멍멍이 한줄일기 수정", required = true)
    String contents;
}
