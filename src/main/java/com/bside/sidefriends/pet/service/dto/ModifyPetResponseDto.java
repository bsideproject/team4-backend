package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import com.bside.sidefriends.pet.domain.PetShareScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Getter
public class ModifyPetResponseDto {

    @NotNull
    private Long petId;

    private PetShareScope shareScope;

    @NotNull
    private String name;

    private String breed;

    private PetGender gender;

    private String birthday;

    private String adoptionDate;

    private Long age;

    private String animalRegistrationNumber;

    private String petImageUrl;
}
