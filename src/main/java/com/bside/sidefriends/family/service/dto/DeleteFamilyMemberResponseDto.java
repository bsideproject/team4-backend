package com.bside.sidefriends.family.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DeleteFamilyMemberResponseDto {

    private Long familyId;

    @Getter
    private List<FamilyMember> familyMemberList;

}
