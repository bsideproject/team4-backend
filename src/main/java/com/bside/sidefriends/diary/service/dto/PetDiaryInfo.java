package com.bside.sidefriends.diary.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PetDiaryInfo {

    private Long diaryId;
    private String writer;
    private String contents;

    // TODO: 날짜 반환 형식
    private LocalDateTime lastModifiedAt;
}
