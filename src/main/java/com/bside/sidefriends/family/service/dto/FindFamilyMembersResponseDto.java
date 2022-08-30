package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FindFamilyMembersResponseDto {

    private Long familyId;
    private int familySize;

    @Getter
    private List<FamilyMember> familyMemberList;

}
