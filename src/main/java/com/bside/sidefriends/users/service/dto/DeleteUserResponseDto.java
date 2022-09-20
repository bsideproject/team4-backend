package com.bside.sidefriends.users.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class DeleteUserResponseDto {

    @NotNull
    @ApiModelProperty(value = "회원 id", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "회원 정보 삭제 여부", required = true)
    private boolean isDeleted;

}
