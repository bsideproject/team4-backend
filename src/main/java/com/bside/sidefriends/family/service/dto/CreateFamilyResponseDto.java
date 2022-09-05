package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateFamilyResponseDto {

    private Long familyId;
    private Long familyManagerId;
    private List<FamilyMember> familyMemberList;

}
