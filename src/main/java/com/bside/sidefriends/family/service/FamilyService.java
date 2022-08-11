package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.family.service.dto.FindFamilyMembersByFamilyIdResponseDto;

public interface FamilyService {

    /**
     * 가족 그룹 생성
     * @param createFamilyRequestDto
     * @return
     */
    CreateFamilyReponseDto createFamily(CreateFamilyRequestDto createFamilyRequestDto);

    /**
     * 가족 그룹 삭제
     * TODO: 그룹장이 아닌 경우 삭제 불가능
     * @param familyId
     * @return
     */
    DeleteFamilyResponseDto deleteFamily(Long familyId);


    /**
     * 가족 그룹 구성원 목록 조회
     * @param familyId
     * @return
     */
    FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId);

    /**
     * 가족 그룹 구성원 추가
     * TODO: 그룹장이 아닌 경우 추가 불가
     * @param familyId
     * @param addFamilyMemberRequestDto
     * @return
     */
    AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto);

    /**
     * 가족 그룹 구성원 삭제
     * TODO: 그룹장이 아닌 경우 삭제 불가
     * @param familyId
     * @param deleteFamilyMemberRequestDto
     * @return
     */
    DeleteFamilyMemberResponseDto deleteFamilyMember(Long familyId, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto);

    ChangeFamilyManagerResponseDto changeFamilyManager(Long familyId, ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto);

}
