package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CreateUserResponseDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
