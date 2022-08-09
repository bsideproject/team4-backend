package com.bside.sidefriends.family.service.dto;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DeleteFamilyMemberRequestDto {

    @NotNull
    private Long deleteMemberId;
}
