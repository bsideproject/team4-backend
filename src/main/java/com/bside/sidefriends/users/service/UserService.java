package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.service.dto.UserCreateRequestDto;
import com.bside.sidefriends.users.service.dto.UserCreateResponseDto;

public interface UserService {

    // 회원 가입
    UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);

    // 이메일로 회원 존재 여부 검증
    boolean validateIfExistsByEmail(String userEmail);

}
