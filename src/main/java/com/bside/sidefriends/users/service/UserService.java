package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.service.dto.*;

public interface UserService {

    /**
     * 회원가입
     * @param userCreateRequestDto 회원가입 요청 DTO
     * @return
     * @throws IllegalStateException
     */
    CreateUserResponseDto createUser(CreateUserRequestDto userCreateRequestDto);

    /**
     * 회원 id로 회원 조회
     * @param userId
     * @return
     */
    FindUserByUserIdResponseDto findUserByUserId(Long userId);

    /**
     * 회원 정보 수정
     * @param userId
     * @param modifyUserRequestDto
     * @return
     */
    ModifyUserResponseDto modifyUser(Long userId, ModifyUserRequestDto modifyUserRequestDto);

    /**
     * 회원 정보 삭제
     * @param userId
     * @return
     */
    DeleteUserResponseDto deleteUser(Long userId);


}
