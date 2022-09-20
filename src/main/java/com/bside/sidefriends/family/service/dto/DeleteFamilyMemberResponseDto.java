package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DeleteFamilyMemberResponseDto {

    @ApiModelProperty(value = "가족 그룹 id", example="1")
    private Long familyId;

    @ApiModelProperty(value = "가족 그룹 인원수", example="3")
    private Long familySize;

    @ApiModelProperty(value="가족 그룹 구성원 리스트")
    private List<FamilyMember> familyMemberList;

}
