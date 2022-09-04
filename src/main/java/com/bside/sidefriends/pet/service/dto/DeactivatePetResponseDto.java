package com.bside.sidefriends.pet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeactivatePetResponseDto {

    private Long petId;
    private boolean isDeactivated;
}
