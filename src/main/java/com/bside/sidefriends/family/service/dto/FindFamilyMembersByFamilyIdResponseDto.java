package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class FindFamilyMembersByFamilyIdResponseDto {

    @Getter
    private List<FamilyMember> familyMemberList;

}
