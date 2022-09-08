package com.bside.sidefriends.family.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AddFamilyMemberRequestDto {

    @NotNull
    private Long groupManagerId;


}
