package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import com.bside.sidefriends.pet.domain.PetShareScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FindPetResponseDto {

    private Long petId;
    private String name;
    private PetShareScope shareScope;
    private Long userId; // 펫 소유 사용자
    private Long familyId; // 펫 공유 가족
    private PetGender gender;
    private String breed;
    private String birthday;
    private Long age;
    private String adoptionDate;
    private String animalRegistrationNumber;
    private String petImageUrl;

}
