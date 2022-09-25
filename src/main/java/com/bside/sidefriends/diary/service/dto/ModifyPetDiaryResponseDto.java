package com.bside.sidefriends.diary.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyPetDiaryResponseDto {

    private Long petId;
    private PetDiaryInfo petDiaryInfo;

}
