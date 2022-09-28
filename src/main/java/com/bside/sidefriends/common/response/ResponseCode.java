package com.bside.sidefriends.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 공통(000-099)
    INVALID_INPUT("001", "입력 값이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED("003", "허용되지 않은 요청 방법입니다."),


    // 로그인(101-150)
    AUTH_TOKEN_EXPIRED("101","기간이 만료된 토큰입니다."),
    AUTH_VERIFICATION_FAIL("102","유효하지 않은 토큰입니다."),
    AUTH_DECODE_FAIL("103","base64 인코딩이 아닙니다."),
    AUTH_FAIL("104","토큰 검증 단계에서 실패하였습니다."),

    // 회원(151-199)
    U_CREATE_SUCCESS("151", "회원 가입에 성공하였습니다."),
    U_FIND_SUCCESS("152", "회원 조회에 성공하였습니다."),
    U_MODIFY_SUCCESS("153", "회원 정보 수정에 성공하였습니다."),
    U_DELETE_SUCCESS("154", "회원 삭제에 성공하였습니다."),
    U_LEAVE_FAMILY_SUCCESS("155", "회원 가족 그룹 탈퇴에 성공하였습니다."),

    U_INVALID_INPUT("160", "회원 서비스 입력 값이 올바르지 않습니다."),
    U_ENTITY_NOT_FOUND("161", "존재하지 않는 회원입니다."),
    U_ENTITY_DUPLICATED("162", "이미 존재하는 회원입니다."),
    U_ENTITY_DELETED("163", "이미 삭제된 회원입니다."),
    U_ENTITY_NOT_UPDATED("164", "수정하려는 회원 정보가 동일합니다."),
    U_DELETE_FAIL("165", "회원을 삭제할 수 없습니다."),

    U_ENTITY_WITH_MANAGER_ROLE("173", "가족 그룹장 권한을 가지고 있는 회원입니다."),
    U_ENTITY_WITHOUT_FAMILY("174", "가족 그룹에 속해 있지 않은 회원입니다."),

    U_IMAGE_UPLOAD_SUCCESS("180", "회원 이미지 업로드에 성공했습니다."),
    U_IMAGE_DOWNLOAD_SUCCESS("181", "회원 이미지 조회에 성공했습니다."),
    U_IMAGE_DELETE_SUCCESS("182", "회원 이미지 삭제에 성공했습니다."),
    
    U_MAIN_PET_NOT_FOUND("191", "회원의 메인 펫 정보가 존재하지 않습니다."),

    // 반려동물(201-299)
    P_CREATE_SUCCESS("201", "펫 생성에 성공하였습니다."),
    P_FIND_SUCCESS("202", "펫 조회에 성공하였습니다."),
    P_FIND_ALL_SUCCESS("203", "사용자의 모든 펫 조회에 성공하였습니다."),
    P_MODIFY_SUCCESS("204", "펫 정보 수정에 성공하였습니다."),
    P_DEACTIVATE_SUCCESS("205", "펫 기록 중지에 성공하였습니다."),
    P_ACTIVATE_SUCCESS("206", "펫 기록 활성화에 성공하였습니다."),
    P_DELETE_SUCCESS("207", "펫 삭제에 성공하였습니다."),
    P_SHARE_SUCCESS("208", "펫 공유에 성공하였습니다."),
    P_UPDATE_MAIN_PET_SUCCESS("209", "대표펫 지정에 성공하였습니다."),

    P_ENTITY_NOT_FOUND("210", "존재하지 않는 펫입니다."),
    P_ENTITY_DEACTIVATED("211", "비활성화된 펫입니다."),
    P_SHARE_FAIL("212", "펫 공유에 실패하였습니다."),
    P_MODIFY_FAIL("213", "펫 정보 수정에 실패하였습니다."),


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
    QUICK_INVALID_INPUT("311","퀵 기록 서비스 입력 값이 올바르지 않습니다."),
    QUICK_NOT_FOUND("312","존재하지 않는 퀵 기록입니다."),
    QUICK_COUNT_EXCEED("313","퀵 기록 실행횟수를 초과하였습니다."),


    // 체크리스트(401-450)
    CHECKLIST_FIND_ALL_SUCCESS("401","할일 전체 조회에 성공하였습니다."),
    CHECKLIST_FIND_ALL_FAIL("402","할일 전체 조회에 실패하였습니다."),
    CHECKLIST_CREATE_SUCCESS("403","할일 생성에 성공하였습니다."),
    CHECKLIST_CREATE_FAIL("404","할일 생성에 실패하였습니다."),
    CHECKLIST_FIND_SUCCESS("405","할일 조회에 성공하였습니다."),
    CHECKLIST_FIND_FAIL("406","할일 조회에 실패하였습니다."),
    CHECKLIST_MODIFY_SUCCESS("407","할일 변경에 성공하였습니다."),
    CHECKLIST_MODIFY_FAIL("408","할일 변경에 실패하였습니다."),
    CHECKLIST_DELETE_SUCCESS("409","할일 삭제에 성공하였습니다."),
    CHECKLIST_DELETE_FAIL("410","할일 삭제에 실패하였습니다."),
    CHECKLIST_CHECKED_SUCCESS("411","할일 수행여부 변경에 성공하였습니다."),
    CHECKLIST_CHECKED_FAIL("412","할일 수행여부 변경에 실패하였습니다."),
    CHECKLIST_INVALID_INPUT("413","할일 서비스 입력 값이 올바르지 않습니다."),
    CHECKLIST_NOT_FOUND("414","존재하지 않는 할일입니다."),
    CHECKLIST_META_NOT_FOUND("415","존재하지 않는 반복 할일 정보입니다."),

    // 할 일, 스케쥴(451-499)
    SCHEDULE_FIND_ALL_SUCCESS("451","일정 전체 조회에 성공하였습니다."),
    SCHEDULE_FIND_ALL_FAIL("452","일정 전체 조회에 실패하였습니다."),
    SCHEDULE_CREATE_SUCCESS("453","일정 생성에 성공하였습니다."),
    SCHEDULE_CREATE_FAIL("454","일정 생성에 실패하였습니다."),
    SCHEDULE_FIND_SUCCESS("455","일정 조회에 성공하였습니다."),
    SCHEDULE_FIND_FAIL("456","일정 조회에 실패하였습니다."),
    SCHEDULE_MODIFY_SUCCESS("457","일정 변경에 성공하였습니다."),
    SCHEDULE_MODIFY_FAIL("458","일정 변경에 실패하였습니다."),
    SCHEDULE_DELETE_SUCCESS("459","일정 삭제에 성공하였습니다."),
    SCHEDULE_DELETE_FAIL("460","일정 삭제에 실패하였습니다."),
    SCHEDULE_INVALID_INPUT("461","일정 서비스 입력 값이 올바르지 않습니다."),
    SCHEDULE_NOT_FOUND("462","존재하지 않는 일정입니다."),
    SCHEDULE_META_NOT_FOUND("463","존재하지 않는 반복 일정 정보입니다."),


    // 양육일지(501-599)
    C_DIARY_CREATE_SUCCESS("501", "한줄일기 생성에 성공하였습니다."),
    C_DIARY_FIND_ALL_SUCCESS("502", "펫 모든 한줄일기 조회에 성공하였습니다."),
    C_DIARY_MODIFY_SUCCESS("503", "한줄일기 수정에 성공하였습니다."),
    C_DIARY_DELETE_SUCCESS("504", "한줄일기 삭제에 성공하였습니다."),

    C_DIARY_ENTITY_NOT_FOUND("510", "존재하지 않는 한줄일기입니다."),
    C_DIARY_MODIFY_NOT_ALLOWED("511", "자신이 작성한 한줄일기만 수정할 수 있습니다."),
    C_DIARY_DELETE_NOT_ALLOWED("512", "자신이 작성한 한줄일기만 삭제할 수 있습니다."),
    C_DIARY_LIMIT_EXCEEDED("513", "하루에 한 개의 한줄일기만 작성할 수 있습니다."),

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

    F_ROLE_MANAGER_REQUIRED("620", "가족 그룹장이 있어야 합니다."),


    // 설정 (700-749)
    FEEDBACK_CREATE_SUCCESS("700", "피드백 전송에 성공하였습니다"),

    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
