package com.bside.sidefriends.users.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value = "회원 정보 수정",
        description = "회원 정보 수정 시 이름만 수정 가능")
public class ModifyUserRequestDto {

    @NotNull
    @ApiModelProperty(value = "회원 이름", required = true)
    private String name;

}
