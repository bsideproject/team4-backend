package com.bside.sidefriends.checklist.service;


import com.bside.sidefriends.checklist.service.dto.*;
import com.bside.sidefriends.users.domain.User;

import java.time.LocalDate;

public interface ChecklistService {

    /**
     * 회원 id, 날짜로 할일 전체 조회
     * @param user 조회할 회원
     * @param date 조회할 날짜
     * @return {@link FindChecklistResponseDto} 조회한 전체 할일 정보 응답 DTO
     */
    FindChecklistResponseDto findChecklist(User user, LocalDate date);

    /**
     * 할일 정보 생성
     * @param user 생성할 회원
     * @param createChecklistRequestDto {@link CreateChecklistRequestDto} 할일 정보 생성 요청 Dto
     * @return {@link CreateChecklistRequestDto} 할일 정보 생성 응답 DTO
     */
    CreateChecklistResponseDto createChecklist(User user, CreateChecklistRequestDto createChecklistRequestDto);

    /**
     * 할일 id로 스케줄 조회
     * @param checklistId 조회할 할일 id
     * @return {@link FindChecklistByChecklistIdResponseDto} 조회한 할일 정보 응답 DTO
     */
    FindChecklistByChecklistIdResponseDto findChecklistByChecklistId(Long checklistId);

    /**
     * 할일 정보 수정
     * @param checklistId 수정할 할일 id
     * @param date 수정할 날짜
     * @param modifyType 수정 타입
     * @param modifyChecklistRequestDto {@link ModifyChecklistRequestDto} 할일 정보 수정 요청 Dto
     * @return {@link ModifyChecklistResponseDto} 할일 정보 수정 응답 DTO
     */
    ModifyChecklistResponseDto modifyChecklist(Long checklistId, LocalDate date, String modifyType, ModifyChecklistRequestDto modifyChecklistRequestDto);

    /** modifyType, deleteType 추가 설명
     * 1. 이 일정만 수정/삭제 = onlyThis
     * 2. 이후 일정 모두 수정삭제 = afterThis
     * 3. 전체 반복 일정 수정/삭제 = all
     */

    /**
     * 할일 정보 삭제
     * @param checklistId 삭제할 할일 id
     * @param date 삭제할 날짜
     * @param deleteType 삭제 타입
     * @return {@link DeleteChecklistResponseDto} 할일 정보 삭제 응답 DTO
     */
    DeleteChecklistResponseDto deleteChecklist(Long checklistId, LocalDate date, String deleteType);

    /**
     * 할일 수행여부 변경
     * @param checklistId 변경할 할일 id
     * @return {@link ModifyCheckedResponseDto} 할일 수행여부 변경 응답 DTO
     */
    ModifyCheckedResponseDto modifyChecked(Long checklistId, LocalDate date);
}
