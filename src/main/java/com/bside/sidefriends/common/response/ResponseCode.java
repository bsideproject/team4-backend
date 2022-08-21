package com.bside.sidefriends.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 공통(00-99)
    INVALID_INPUT("001", "입력 값이 올바르지 않습니다."),

    // 로그인(101-150)

    // 회원(151-199)

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
