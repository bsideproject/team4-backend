package com.bside.sidefriends.pet.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateMainPetRequestDto {

    @NotNull
    private Long mainPetId;
}
