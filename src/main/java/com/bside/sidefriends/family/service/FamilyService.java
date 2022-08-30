package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.service.dto.*;

public interface FamilyService {

    /**
     * 가족 그룹 구성원 목록 조회
     * @param username 가족 그룹을 조회할 회원 id
     * @return {@link FindFamilyMembersResponseDto} 가족 그룹 조회 응답 DTO
     */
    FindFamilyMembersResponseDto findFamilyMembers(String username);

    /**
     * 가족 그룹 삭제
     * @param username 삭제할 가족 그룹의 그룹장 username
     * @return {@link DeleteFamilyResponseDto} 가족 그룹 삭제 응답 DTO
     */
    DeleteFamilyResponseDto deleteFamily(String username);


    /**
     * 가족 그룹 구성원 추가
     * @param username 구성원을 추가할 가족 그룹의 그룹장 username
     * @param addFamilyMemberRequestDto {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 추가 요청 DTO
     * @return {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 추가 응답 DTO
     */
    AddFamilyMemberResponseDto addFamilyMember(String username, AddFamilyMemberRequestDto addFamilyMemberRequestDto);

    /**
     * 가족 그룹 구성원 삭제
     * @param username 구성원을 삭제할 가족 그룹의 그룹장 username
     * @param deleteFamilyMemberRequestDto {@link DeleteFamilyMemberRequestDto} 가족 그룹 구성원 삭제 요청 DTO
     * @return {@link AddFamilyMemberResponseDto} 가족 그룹 구성원 삭제 응답 DTO
     */
    DeleteFamilyMemberResponseDto deleteFamilyMember(String username, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto);

    /**
     * 가족 그룹 그룹장 변경
     * @param username 그룹장을 변경할 가족 그룹의 그룹장 username
     * @param changeFamilyManagerRequestDto {@link ChangeFamilyManagerRequestDto} 가족 그룹장 변경 요청 DTO
     * @return {@link ChangeFamilyManagerResponseDto} 가족 그룹장 변경 응답 DTO
     */
    ChangeFamilyManagerResponseDto changeFamilyManager(String username, ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto);

    /**
     * 가족 그룹 생성
     * @param createFamilyRequestDto {@link CreateFamilyRequestDto} 가족 그룹 생성 요청 DTO
     * @return {@link CreateFamilyReponseDto} 가족 그룹 생성 응답 DTO
     */
    CreateFamilyReponseDto createFamily(CreateFamilyRequestDto createFamilyRequestDto);


}
