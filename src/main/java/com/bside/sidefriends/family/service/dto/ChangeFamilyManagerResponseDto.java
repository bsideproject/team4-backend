package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChangeFamilyManagerResponseDto {

    private Long familyId;

    private Long familyManagerId;

    private int familySize;

    @Getter
    private List<FamilyMember> familyMemberList;

}
