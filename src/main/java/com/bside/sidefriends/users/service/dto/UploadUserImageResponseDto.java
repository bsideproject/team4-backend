package com.bside.sidefriends.users.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UploadUserImageResponseDto {

    @NotNull
    @ApiModelProperty(value = "회원 id", required = true)
    Long userId;

    @URL
    @ApiModelProperty(value = "회원 프로필 이미지 url", required = true)
    String imageUrl;

}
