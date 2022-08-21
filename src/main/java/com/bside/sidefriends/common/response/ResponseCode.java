package com.bside.sidefriends.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 공통(000-099)
    INVALID_INPUT("001", "입력 값이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED("003", "허용되지 않은 요청 방법입니다."),


    // 로그인(101-150)

    // 회원(151-199)
    U_CREATE_SUCCESS("151", "회원 가입에 성공하였습니다."),
    U_FIND_SUCCESS("152", "회원 조회에 성공하였습니다."),
    U_MODIFY_SUCCESS("153", "회원 정보 수정에 성공하였습니다."),
    U_DELETE_SUCCESS("154", "회원 삭제에 성공하였습니다."),

    U_INVALID_INPUT("160", "회원 서비스 입력 값이 올바르지 않습니다."),
    U_ENTITY_NOT_FOUND("161", "존재하지 않는 회원입니다."),
    U_ENTITY_DUPLICATED("162", "이미 존재하는 회원입니다."),
    U_ENTITY_DELETED("163", "이미 삭제된 회원입니다."),
    U_ENTITY_NOT_UPDATED("164", "수정하려는 회원 정보가 동일합니다."),


    // 반려동물(201-299)

    // 퀵 기록(301-399)

    CREATE_QUICK_SUCCESS("301", "퀵 기록 생성에 성공하였습니다."),
    CREATE_QUICK_FAIL("302", "퀵 기록 생성에 실패하였습니다."),
    FIND_QUICK_SUCCESS("303", "퀵 기록 조회에 성공하였습니다."),
    FIND_QUICK_FAIL("304", "퀵 기록 조회에 실패하였습니다."),
    MODIFY_QUICK_SUCCESS("305", "퀵 기록 수정에 성공하였습니다."),
    MODIFY_QUICK_FAIL("306", "퀵 기록 수정에 실패하였습니다."),
    MODIFY_QUICK_ORDER_SUCCESS("307", "퀵 기록 순서 변경에 성공하였습니다."),
    MODIFY_QUICK_ORDER_FAIL("308", "퀵 기록 순서 변경에 실패하였습니다."),
    MODIFY_QUICK_COUNT_SUCCESS("309", "퀵 기록 실행 횟수 증가에 성공하였습니다."),
    MODIFY_QUICK_COUNT_FAIL("310", "퀵 기록 실행 횟수 증가에 실패하였습니다."),


    // 체크리스트(401-450)

    // 할 일, 스케쥴(451-499)

    // 양육일지(501-599)

    // 가족 그룹(601-699)
    F_CREATE_SUCCESS("601", "가족 그룹 생성에 성공하였습니다."),
    F_FIND_SUCCESS("602", "가족 그룹 조회에 성공하였습니다."),
    F_DELETE_SUCCESS("603", "가족 그룹 삭제에 성공하였습니다."),
    F_MODIFY_MANAGER_SUCCESS("604", "가족 그룹장 변경에 성공하였습니다."),
    F_ADD_MEMBER_SUCCESS("605", "가족 그룹 구성원 추가에 성공하였습니다."),
    F_DELETE_MEMBER_SUCCESS("606", "가족 그룹 구성원 삭제에 성공하였습니다."),

    F_INVALID_INPUT("610", "가족 서비스 입력 값이 올바르지 않습니다."),
    F_ENTITY_NOT_FOUND("611", "존재하지 않는 가족 그룹입니다."),
    F_ENTITY_DELETED("612", "이미 삭제된 가족 그룹입니다."),
    F_MAX_NUMBER_EXCEEDED("613", "가족 그룹 정원을 초과하였습니다."),
    F_DELETE_NOT_ALLOWED("614", "가족 그룹을 삭제할 수 없습니다."),
    F_DELETE_MEMBER_NOT_ALLOWED("615", "가족 그룹 구성원을 추방할 수 없습니다."),
    F_MEMBER_DUPLICATED("616", "가족 그룹에 속해 있는 사용자입니다."),
    F_MEMBER_WITH_NO_FAMILY("617", "가족 그룹에 속해 있지 않은 사용자입니다."),
    F_MEMBER_NOT_FOUND("618", "가족 그룹 구성원이 존재하지 않습니다."),
    F_MANAGER_NOT_FOUND("619", "가족 그룹 그룹장이 존재하지 않습니다."),

    F_ROLE_MANAGER_REQUIRED("620", "가족 그룹장 권한이 있어야 합니다."),


    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
