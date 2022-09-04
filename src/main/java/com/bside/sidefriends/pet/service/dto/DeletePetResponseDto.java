package com.bside.sidefriends.pet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePetResponseDto {

    private Long petId;
    private boolean isDeleted;
}
