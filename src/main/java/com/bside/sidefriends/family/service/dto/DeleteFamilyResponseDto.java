package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class DeleteFamilyResponseDto {

    @NotNull
    @ApiModelProperty(value = "가족 그룹 id", example = "1")
    private Long familyId;

    @NotNull
    @ApiModelProperty(value = "가족 그룹 삭제 여부", example = "true")
    private boolean isDeleted;
}
