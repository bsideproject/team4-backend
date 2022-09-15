package com.bside.sidefriends.users.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원 가입")
@Builder
public class CreateUserRequestDto {

    @NotNull
    @ApiModelProperty(value = "회원 이름", required = true)
    String name;

    @NotNull
    @ApiModelProperty(value = "회원 이메일", required = true)
    String email;

    @NotNull
    @ApiModelProperty(value = "oauth 제공자", required = true)
    String provider;

    @NotNull
    @ApiModelProperty(value = "oauth 회원 id", required = true)
    String providerId;

}