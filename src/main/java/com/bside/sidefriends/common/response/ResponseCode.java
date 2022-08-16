package com.bside.sidefriends.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 로그인(101-150)

    // 회원(151-199)
    U_CREATE_SUCCESS("151", "회원 가입에 성공하였습니다."),
    U_FIND_SUCCESS("152", "회원 조회에 성공하였습니다."),
    U_MODIFY_SUCCESS("153", "회원 정보 수정에 성공하였습니다."),
    U_DELETE_SUCCESS("154", "회원 삭제에 성공하였습니다."),
    U_INVALID_INPUT("160", "회원 서비스 입력 값이 올바르지 않습니다."),
    U_ENTITY_NOT_FOUND("161", "회원을 찾을 수 없습니다."),
    U_ENTITY_DUPLICATED("162", "이미 존재하는 회원입니다."),
    U_ENTITY_DELETED("163", "이미 삭제된 회원입니다."),
    U_ENTITY_NOT_UPDATED("164", "수정하려는 회원 정보가 동일합니다."),

    U_ENTITY_WITH_FAMILY("170", "사용자가 이미 가족 그룹에 속해 있습니다."),
    U_ENTITY_WITH_NO_FAMILY("171", "사용자가 아무 가족 그룹에도 속해 있지 않습니다."),
    U_ENTITY_WITH_WRONG_FAMILY("172", "사용자가 해당 가족 그룹에 속해 있지 않습니다."),
    U_ENTITY_WITH_MANAGER_ROLE("173", "사용자가 가족 그룹장 권한을 가지고 있습니다."),

    // 반려동물(201-299)

    // 퀵 기록(301-399)

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
    F_ENTITY_NOT_FOUND("611", "가족 그룹을 찾을 수 없습니다."),
    F_ENTITY_DELETED("612", "이미 삭제된 가족 그룹입니다."),
    F_MAX_NUMBER_EXCEEDED("613", "가족 그룹 정원을 초과하였습니다."),

    F_ROLE_MANAGER_REQUIRED("620", "가족 그룹장 권한이 있어야 합니다."),


    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
