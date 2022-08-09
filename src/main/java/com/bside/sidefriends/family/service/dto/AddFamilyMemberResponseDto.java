package com.bside.sidefriends.family.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddFamilyMemberResponseDto {

    private Long familyId;

    @Getter
    private List<FamilyMember> familyMembersList;

    @Getter
    @AllArgsConstructor
    public static class FamilyMember {
        private Long userId;
        private String name;
        private User.Role role;
    }
}
