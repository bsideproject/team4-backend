package com.bside.sidefriends.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 로그인(101-150)

    // 회원(151-199)
    CREATE_USER_SUCCESS("151", "회원 가입에 성공하였습니다."),
    CREATE_USER_FAIL("152", "회원 가입에 실패하였습니다."),
    FIND_USER_SUCCESS("153", "회원 조회에 성공하였습니다."),
    FIND_USER_FAIL("154", "회원 조회에 실패하였습니다"),
    MODIFY_USER_SUCCESS("155", "회원 정보 수정에 성공하였습니다."),
    MODIFY_USER_FAIL("156", "회원 정보 수정에 실패하였습니다."),
    DELETE_USER_SUCCESS("157", "회원 삭제에 성공하였습니다."),
    DELETE_USER_FAIL("158", "회원 삭제에 실패하였습니다.")

    // 반려동물(201-299)

    // 퀵 기록(301-399)

    // 체크리스트(401-450)

    // 할 일, 스케쥴(451-499)

    // 양육일지(501-599)

    // 가족 그룹(601-699)

    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
