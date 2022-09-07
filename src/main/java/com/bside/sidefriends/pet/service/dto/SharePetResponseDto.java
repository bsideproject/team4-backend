package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetShareScope;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SharePetResponseDto {

    private Long petId;
    private PetShareScope shareScope;
    private Long familyId;

}
