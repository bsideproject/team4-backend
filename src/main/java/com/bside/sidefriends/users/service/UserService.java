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

}
