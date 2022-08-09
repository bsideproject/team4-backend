package com.bside.sidefriends.users.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    @DisplayName("소셜 로그인 인증을 통해 받아 온 회원 이름, 이메일 정보로 회원 가입에 성공한다.")
    void createUser() {}

    @Test
    @DisplayName("회원 이름이 없는 경우 회원 가입에 실패한다.")
    void whenCreateUserWithNoName_thenThrowsException() {}

    @Test
    @DisplayName("회원 이메일이 없는 경우 회원 가입에 실패한다.")
    void whenCreateUserWithNoEmail_thenThrowsException() {}

    @Test
    @DisplayName("동일한 소셜 인증 정보가 존재하는 경우 경우 회원 가입에 실패한다.")
    void whenCreateUserWithDuplicateUsername_thenThrowsException() {}

    @Test
    @DisplayName("회원 id로 회원 조회에 성공한다.")
    void findUserByUserId_success() {}

    @Test
    @DisplayName("존재하지 않는 회원인 경우 회원 조회에 실패한다.")
    void whenFindNonExistentUser_thenThrowsException() {}

    @Test
    @DisplayName("삭제된 회원인 경우 회원 조회에 실패한다.")
    void whenFindDeletedUser_thenThrowsException() {}

    @Test
    @DisplayName("회원 수정에 성공한다.")
    void modifyUser_success() {}

    @Test
    @DisplayName("존재하지 않는 회원인 경우 회원 수정에 실패한다.")
    void whenModifyNonExistentUser_thenThrowsException() {}

    @Test
    @DisplayName("삭제된 회원인 경우 회원 조회에 실패한다.")
    void whenModifyDeletedUser_thenThrowsException() {}

    @Test
    @DisplayName("수정하려는 회원 이름이 이전과 동일한 경우 회원 정보 수정에 실패한다.")
    void whenModifyWithSameName_thenThrowsException() {}

    @Test
    @DisplayName("회원 삭제에 성공한다.")
    void deleteUser_success() {}

    @Test
    @DisplayName("존재하지 않는 회원인 경우 회원 삭제에 실패한다.")
    void whenDeleteNonExistentUser_thenThrowsException() {}

    @Test
    @DisplayName("이미 삭제된 회원인 경우 회원 삭제에 실패한다.")
    void whenDeleteDeletedUser_thenThrowsException() {}

    @Test
    @DisplayName("그룹장인 경우 회원 삭제에 실패한다.")
    void whenDeleteRoleManagerUser_thenThrowsException() {}

}