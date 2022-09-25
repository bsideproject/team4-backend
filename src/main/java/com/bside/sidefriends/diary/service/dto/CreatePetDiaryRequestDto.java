package com.bside.sidefriends.diary.service.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class CreatePetDiaryRequestDto {

    private Long writerId;

    @Size(min = 1, message = "한 줄 일기 내용은 1자 이상이어야 합니다.")
    @Size(max = 140, message = "한 줄 일기 내용은 140자 이하여야 합니다.")
    private String contents;

}
