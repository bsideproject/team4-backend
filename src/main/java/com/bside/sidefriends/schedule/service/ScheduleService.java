package com.bside.sidefriends.schedule.service;

import com.bside.sidefriends.schedule.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.service.dto.FindUserByUserIdResponseDto;
import com.bside.sidefriends.users.service.dto.ModifyUserRequestDto;
import com.bside.sidefriends.users.service.dto.ModifyUserResponseDto;

import java.time.LocalDate;

public interface ScheduleService {

    /**
     * 회원 id, 날짜로 스케줄 전체 조회
     * @param user 조회할 회원
     * @param date 조회할 날짜
     * @return {@link FindScheduleResponseDto} 조회한 전체 스케줄 정보 응답 DTO
     */
    FindScheduleResponseDto findSchedule(User user, LocalDate date);

    /**
     * 스케줄 정보 생성
     * @param user 생성할 회원
     * @param createScheduleRequestDto {@link CreateScheduleRequestDto} 스케줄 정보 생성 요청 Dto
     * @return {@link CreateScheduleResponseDto} 스케줄 정보 생성 응답 DTO
     */
    CreateScheduleResponseDto createSchedule(User user, CreateScheduleRequestDto createScheduleRequestDto);

    /**
     * 스케줄 id로 스케줄 조회
     * @param scheduleId 조회할 스케줄 id
     * @return {@link FindScheduleByScheduleIdResponseDto} 조회한 스케줄 정보 응답 DTO
     */
    FindScheduleByScheduleIdResponseDto findScheduleByScheduleId(Long scheduleId);

    /**
     * 스케줄 정보 수정
     * @param scheduleId 수정할 스케줄 id
     * @param date 수정할 날짜
     * @param modifyType 수정 타입
     * @param modifyScheduleRequestDto {@link ModifyScheduleRequestDto} 스케줄 정보 수정 요청 Dto
     * @return {@link ModifyScheduleResponseDto} 스케줄 정보 수정 응답 DTO
     */
    ModifyScheduleResponseDto modifySchedule(Long scheduleId, LocalDate date, String modifyType, ModifyScheduleRequestDto modifyScheduleRequestDto);

    /** modifyType, deleteType 추가 설명
    * 1. 이 일정만 수정/삭제 = onlyThis
    * 2. 이후 일정 모두 수정삭제 = afterThis
    * 3. 전체 반복 일정 수정/삭제 = all
    */

    /**
     * 스케줄 정보 삭제
     * @param scheduleId 삭제할 스케줄 id
     * @param date 삭제할 날짜
     * @param deleteType 삭제 타입
     * @return {@link DeleteScheduleResponseDto} 스케줄 정보 삭제 응답 DTO
     */
    DeleteScheduleResponseDto deleteSchedule(Long scheduleId, LocalDate date, String deleteType);
}
