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

    // TODO: String으로 해도 될지, 그냥 isShared로 주는 게 나을지
    private PetShareScope shareScope;
    private PetGender gender;
    private String breed;
    private String birthday;
    private Long age;
    private String adoptionDate;
    private String animalRegistrationNumber;

    // TODO: 펫 이미지
}
