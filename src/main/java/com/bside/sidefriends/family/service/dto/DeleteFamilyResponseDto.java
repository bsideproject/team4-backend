package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class DeleteFamilyResponseDto {

    @NotNull
    private Long familyId;

    @NotNull
    private boolean isDeleted;
}
