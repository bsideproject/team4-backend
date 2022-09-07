package com.bside.sidefriends.pet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindAllPetResponseDto {

    private Long userId;
    private Long mainPetId;
    private int totalPetNumber;
    private List<PetInfo> petList;

}
