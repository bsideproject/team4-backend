package com.bside.sidefriends.family.service.dto;

import com.bside.sidefriends.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FamilyMember {

    private Long userId;
    private String name;
    private User.Role role;
    private String userImageUrl;

}
