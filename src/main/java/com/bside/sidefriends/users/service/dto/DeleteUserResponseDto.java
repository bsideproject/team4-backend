package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteUserResponseDto {

    private Long id;
    private boolean isDeleted;

}
