package com.bside.sidefriends.diary.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePetDiaryResponseDto {

    private Long petId;

    private PetDiaryInfo petDiaryInfo;

}
