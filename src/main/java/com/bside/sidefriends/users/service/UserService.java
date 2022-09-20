package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.service.dto.*;

public interface UserService {

    /**
     * 회원가입
     * @param userCreateRequestDto {@link CreateUserRequestDto} 회원가입 요청 DTO
     * @return {@link CreateUserResponseDto} 가입된 회원 정보 응답 DTO
     * @throws IllegalStateException
     */
    CreateUserResponseDto createUser(CreateUserRequestDto userCreateRequestDto);

    /**
     * 회원 id로 회원 조회
     * @param username 조회할 회원 username
     * @return {@link FindUserByUserIdResponseDto} 조회할 회원 정보 응답 DTO
     */
    FindUserByUserIdResponseDto findUserByUserId(String username);

    /**
     * 회원 정보 수정
     * @param username 정보를 수정할 회원 username
     * @param modifyUserRequestDto {@link ModifyUserRequestDto} 회원 정보 수정 요청 Dto
     * @return {@link ModifyUserResponseDto} 회원 정보 수정 응답 DTO
     */
    ModifyUserResponseDto modifyUser(String username, ModifyUserRequestDto modifyUserRequestDto);

    /**
     * 회원 정보 삭제
     * @param username 삭제할 회원 username
     * @return {@link DeleteUserResponseDto} 회원 정보 삭제 응답 DTO
     */
    DeleteUserResponseDto deleteUser(String username);

    /**
     * 회원 가족 그룹 탈퇴
     * @param username 가족 그룹을 탈퇴할 회원 username
     * @return {@link LeaveFamilyResponseDto} 회원 가족 탈퇴 응답 DTO
     */
    LeaveFamilyResponseDto leaveFamily(String username);


}
