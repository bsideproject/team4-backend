package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.pet.domain.PetGender;
import com.bside.sidefriends.pet.domain.PetShareScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder()
public class CreatePetResponseDto {

    @NotNull
    private Long petId;

    // TODO: 유저 정보 응답 필요성 검토
    private Long userId;

    // TODO: 가족 정보 응답 필요성 검토
    private Family family;

    // TODO: 공유 타입 속성 필요성 검토
    @NotNull
    private PetShareScope shareScope;

    @NotNull
    private String name;

    private String breed;

    private PetGender gender;

    private String birthday;

    private String adoptionDate;

    private Long age;

    private String animalRegistrationNumber;

    private boolean isDeactivated;

    private String petImageUrl;
}
