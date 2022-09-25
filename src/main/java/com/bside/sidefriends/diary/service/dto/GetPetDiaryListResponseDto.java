package com.bside.sidefriends.diary.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPetDiaryListResponseDto {

    int totalNum;
    List<PetDiaryInfo> petDiaryList;
}
