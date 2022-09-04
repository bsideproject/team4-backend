package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.domain.PetGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetRequestDto {

    @NotNull
    private String name;

    private PetGender gender;

    private String breed;

    private String birthday;

    private Long age;

    private String adoptionDate;

    private String animalRegistrationNumber;

}
