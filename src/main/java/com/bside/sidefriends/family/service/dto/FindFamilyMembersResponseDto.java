package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class FindFamilyMembersResponseDto {

    private long familySize;
    private List<FamilyMember> familyMemberList;

}
