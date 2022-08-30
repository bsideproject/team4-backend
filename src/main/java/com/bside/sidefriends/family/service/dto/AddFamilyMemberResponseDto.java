package com.bside.sidefriends.family.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddFamilyMemberResponseDto {

    private Long familyId;

    @Getter
    private List<FamilyMember> familyMemberList;

}
