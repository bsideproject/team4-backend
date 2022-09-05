package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.family.service.dto.FindFamilyMembersByFamilyIdResponseDto;

public interface FamilyService {

    /**
     * 가족 그룹 생성
     * @param memberUsername 가족 그룹의 구성원으로 추가할 사용자 username
     * @param createFamilyRequestDto {@link CreateFamilyRequestDto} 가족 그룹 생성 요청 DTO
     * @return {@link CreateFamilyReponseDto} 가족 그룹 생성 응답 DTO
     */
    CreateFamilyReponseDto createFamily(String memberUsername, CreateFamilyRequestDto createFamilyRequestDto);

    /**
     * 가족 그룹 삭제
     * TODO: 그룹장이 아닌 경우 삭제 불가능
     * @param familyId 삭제할 가족 그룹 id
     * @return {@link DeleteFamilyResponseDto} 가족 그룹 삭제 응답 DTO
     */
    DeleteFamilyResponseDto deleteFamily(Long familyId);

    /**
     * 가족 그룹 구성원 목록 조회
     * @param familyId 조회할 가족 그룹 id
     * @return {@link FindFamilyMembersByFamilyIdResponseDto} 가족 그룹 조회 응답 DTO
     */
    FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId);

    /**
     * 가족 그룹 구성원 추가
     * TODO: 그룹장이 아닌 경우 추가 불가
     * @param familyId 구성원을 추가할 가족 그룹 id
     * @param addFamilyMemberRequestDto {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 추가 요청 DTO
     * @return {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 추가 응답 DTO
     */
    AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto);

    /**
     * 가족 그룹 구성원 삭제
     * TODO: 그룹장이 아닌 경우 삭제 불가
     * @param familyId 구성원을 삭제할 가족 그룹 Id
     * @param deleteFamilyMemberRequestDto {@link DeleteFamilyMemberRequestDto} 가족 그룹 구성원 삭제 요청 DTO
     * @return {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 삭제 응답 DTO
     */
    DeleteFamilyMemberResponseDto deleteFamilyMember(Long familyId, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto);

    /**
     * 가족 그룹 그룹장 변경
     * TODO: 그룹장이 아닌 경우 변경 불가
     * @param familyId 그룹장을 변경할 가족 그룹 id
     * @param changeFamilyManagerRequestDto {@link ChangeFamilyManagerRequestDto} 가족 그룹장 변경 요청 DTO
     * @return {@link ChangeFamilyManagerResponseDto} 가족 그룹장 변경 응답 DTO
     */
    ChangeFamilyManagerResponseDto changeFamilyManager(Long familyId, ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto);

}
