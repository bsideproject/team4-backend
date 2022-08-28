package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UploadUserImageResponseDto {

    @NotNull
    Long userId;

    @URL
    String imageUrl;

}
