package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.family.service.dto.FindFamilyMembersByFamilyIdResponseDto;

public interface FamilyService {

    /**
     * 가족 계정 생성
     * @param createFamilyRequestDto
     * @return
     */
    CreateFamilyReponseDto createFamily(CreateFamilyRequestDto createFamilyRequestDto);

    /**
     * 가족 구성원 목록 조회
     * @param familyId
     * @return
     */
    FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId);

    /**
     * 가족 구성원 추가
     * @param familyId
     * @param addFamilyMemberRequestDto
     * @return
     */
    AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto);

    /**
     * 가족 구성원 삭제
     * @param familyId
     * @param deleteFamilyMemberRequestDto
     * @return
     */
    DeleteFamilyMemberResponseDto deleteFamilyMember(Long familyId, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto);

    // TODO: 가족 그룹장 권한 변경

}
