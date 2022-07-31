package com.bside.sidefriends.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserCreateResponseDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
