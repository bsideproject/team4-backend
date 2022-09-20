package com.bside.sidefriends.users.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
@AllArgsConstructor
public class GetUserImageResponseDto {

    @ApiModelProperty(value = "회원 id", required = true)
    private Long userId;

    @URL
    @ApiModelProperty(value = "회원 프로필 이미지 url")
    private String userImageUrl;
}
