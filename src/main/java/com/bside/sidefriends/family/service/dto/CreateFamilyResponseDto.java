package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateFamilyResponseDto {

    @ApiModelProperty(value = "가족 그룹 id", example="1")
    private Long familyId;

    @ApiModelProperty(value = "가족 그룹 그룹장 회원 id", example="1")
    private Long familyManagerId;

    @ApiModelProperty(value = "가족 그룹 구성원 리스트")
    private List<FamilyMember> familyMemberList;

}
