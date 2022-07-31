package com.bside.sidefriends.user.service.dto;

import java.time.LocalDateTime;

@Getter
public class UserCreateResponseDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
