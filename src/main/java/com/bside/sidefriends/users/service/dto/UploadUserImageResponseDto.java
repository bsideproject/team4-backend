package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadUserImageResponseDto {

    Long userId;

    @Getter
    String imageUrl;

}
