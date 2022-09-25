package com.bside.sidefriends.diary.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PetDiaryInfo {

    private Long diaryId;
    private String writer;
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastModifiedAt;
}
