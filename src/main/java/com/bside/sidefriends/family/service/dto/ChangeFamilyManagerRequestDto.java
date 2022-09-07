package com.bside.sidefriends.family.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ChangeFamilyManagerRequestDto {

    @NotNull
    private Long prevManagerId;

    @NotNull
    private Long nextManagerId;
}
