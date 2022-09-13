package com.bside.sidefriends.family.service.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@ApiModel(value="가족 그룹 구성원 내보내기")
public class DeleteFamilyMemberRequestDto {

    @NotNull
    @ApiModelProperty(value = "가족 그룹 구성원 회원 id", example = "2")
    private Long deleteMemberId;
}
