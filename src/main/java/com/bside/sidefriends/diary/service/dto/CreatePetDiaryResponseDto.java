package com.bside.sidefriends.diary.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePetDiaryResponseDto {

    private Long diaryId;

    private Long petId;

    private String writer;

    private String contents;

}
