package com.bside.sidefriends.diary.service;

import com.bside.sidefriends.diary.service.dto.*;

public interface DiaryService {

    /**
     * 펫 한줄일기 작성
     * @param petId 한줄일기를 작성할 펫 id
     * @param createDiaryRequestDto {@link CreatePetDiaryRequestDto} 펫 한줄일기 생성 요청 DTO
     * @return {@link CreatePetDiaryResponseDto} 펫 한줄일기 생성 응답 DTO
     */
    CreatePetDiaryResponseDto createPetDiary(Long petId, CreatePetDiaryRequestDto createDiaryRequestDto);

    /**
     * 펫 한줄일기 리스트 조회
     * @param petId 한줄일기를 조회할 펫 id
     * @return {@link GetPetDiaryResponseDto} 펫 한줄일기 리스트 조회 응답 DTO
     */
    GetPetDiaryResponseDto getPetDiaryList(Long petId);

    /**
     * 펫 한줄일기 수정
     * @param petId 한줄일기를 수정할 펫 id
     * @param modifyDiaryRequestDto {@link ModifyPetDiaryRequestDto} 펫 한줄일기 수정 요청 DTO
     * @return {@link ModifyPetDiaryResponseDto} 펫 한줄일기 수정 응답 DTO
     */
    ModifyPetDiaryResponseDto modifyPetDiary(Long petId, ModifyPetDiaryRequestDto modifyDiaryRequestDto);

    /**
     * 펫 한줄일기 삭제
     * @param diaryId 삭제할 펫 한줄일기 id
     * @return {@link DeletePetDiaryResponseDto} 펫 한줄일기 삭제 응답 DTO
     */
    DeletePetDiaryResponseDto deletePetDiary(Long diaryId);

}