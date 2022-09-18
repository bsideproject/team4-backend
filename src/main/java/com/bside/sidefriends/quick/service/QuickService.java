package com.bside.sidefriends.quick.service;

import com.bside.sidefriends.quick.service.dto.*;
import com.bside.sidefriends.users.domain.User;

import java.time.LocalDate;

public interface QuickService {

    /**
     * 퀵기록 최초 생성
     * @param user @LoginUser
     * @return {@link CreateQuickResponseDto} 퀵기록 생성 응답 DTO
     */
    CreateQuickResponseDto createDefaultQuick(User user);


    /**
     * 퀵기록 목록 조회
     * @param user @LoginUser
     * @param date 조회할 날짜 (yyyy-MM-dd)
     * @return {@link FindQuickByPetIdResponseDto} 퀵기록 조회 응답 DTO
     */
    FindQuickByPetIdResponseDto findQuickByPetId(User user, LocalDate date);


    /**
     * 퀵기록 수정
     * @param quickId 수정할 퀵 id
     * @param changeQuickRequestDto {@link ChangeQuickRequestDto} 퀵기록 변경 요청 DTO
     * @return {@link ChangeQuickResponseDto} 퀵기록 수정 응답 DTO
     */
    ChangeQuickResponseDto changeQuick(Long quickId, ChangeQuickRequestDto changeQuickRequestDto);


    /**
     * 퀵기록 순서 수정
     * @param user @LoginUser
     * @param changeQuickOrderRequestDto {@link ChangeQuickOrderRequestDto} 퀵기록 순서 변경 요청 DTO
     * @return {@link ChangeQuickOrderResponseDto} 퀵기록 순서 변경 응답 DTO
     */
    ChangeQuickOrderResponseDto changeQuickOrder(User user, ChangeQuickOrderRequestDto changeQuickOrderRequestDto);


    /**
     * 퀵기록 실행횟수 수정
     * @param quickId 수정할 퀵 id
     * @return {@link ChangeQuickCountResponseDto} 퀵기록 실행횟수 변경 응답 DTO
     */
    ChangeQuickCountResponseDto changeQuickCount(Long quickId, LocalDate date);
    
}
