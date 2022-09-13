package com.bside.sidefriends.family.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FindFamilyMembersResponseDto {

    @ApiModelProperty(value = "가족 그룹 인원수")
    private long familySize;

    @ApiModelProperty(value = "가족 그룹 구성원 리스트")
    private List<FamilyMember> familyMemberList;

}
