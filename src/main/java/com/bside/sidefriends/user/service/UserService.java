package com.bside.sidefriends.user.service;

import com.bside.sidefriends.user.domain.User;
import com.bside.sidefriends.user.service.dto.UserCreateRequestDto;
import com.bside.sidefriends.user.service.dto.UserCreateResponseDto;

public interface UserService {

    // 회원 가입
    UserCreateResponseDto addUser(UserCreateRequestDto userCreateRequestDto);

    // 이메일로 회원 조회
    boolean validateUserByEmail(String userEmail);

}
