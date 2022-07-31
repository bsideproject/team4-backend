package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.service.dto.UserCreateRequestDto;
import com.bside.sidefriends.users.service.dto.UserCreateResponseDto;

import java.util.Optional;

public interface UserService {

    /**
     * 회원가입
     * @param userCreateRequestDto 회원가입 요청 DTO
     * @return
     * @throws IllegalStateException
     */
    UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);

    /**
     * 이메일 주소로 이미 존재하는 회원인지 검사
     * @param email 존재 여부를 검사할 회원의 이메일
     * @return 회원 존재 여부
     */
    boolean checkIfExistsByEmail(String email);

}
