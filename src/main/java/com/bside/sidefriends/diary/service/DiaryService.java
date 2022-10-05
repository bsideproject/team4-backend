package com.bside.sidefriends.diary.service;

import com.bside.sidefriends.diary.service.dto.*;

public interface DiaryService {

    /**
     * 펫 한줄일기 작성
     * @param petId 한줄일기를 작성할 펫 id
     * @param username 한줄일기를 작성할 회원 username
     * @param createDiaryRequestDto {@link CreatePetDiaryRequestDto} 펫 한줄일기 생성 요청 DTO
     * @return {@link CreatePetDiaryResponseDto} 펫 한줄일기 생성 응답 DTO
     */
    CreatePetDiaryResponseDto createPetDiary(Long petId, String username, CreatePetDiaryRequestDto createDiaryRequestDto);

    /**
     * 펫 한줄일기 리스트 조회
     * @param petId 한줄일기를 조회할 펫 id
     * @return {@link GetPetDiaryListResponseDto} 펫 한줄일기 리스트 조회 응답 DTO
     */
    GetPetDiaryListResponseDto getPetDiaryList(Long petId);

    /**
     * 펫 한줄일기 수정
     * @param diaryId 수정할 한줄일기 id
     * @param username 한줄일기를 수정할 회원 username
     * @param modifyDiaryRequestDto {@link ModifyPetDiaryRequestDto} 펫 한줄일기 수정 요청 DTO
     * @return {@link ModifyPetDiaryResponseDto} 펫 한줄일기 수정 응답 DTO
     */
    ModifyPetDiaryResponseDto modifyPetDiary(Long diaryId, String username, ModifyPetDiaryRequestDto modifyDiaryRequestDto);

    /**
     * 펫 한줄일기 삭제
     * @param diaryId 삭제할 펫 한줄일기 id
     * @param username 한줄일기를 삭제할 회원 username
     * @return {@link DeletePetDiaryResponseDto} 펫 한줄일기 삭제 응답 DTO
     */
    DeletePetDiaryResponseDto deletePetDiary(Long diaryId, String username);

}
