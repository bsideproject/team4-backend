package com.bside.sidefriends.pet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateMainPetResponseDto {

    private Long userId;
    private Long mainPetId;
    private PetInfo petInfo;

}
