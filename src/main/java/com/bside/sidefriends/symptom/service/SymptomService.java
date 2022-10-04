package com.bside.sidefriends.symptom.service;

import com.bside.sidefriends.symptom.service.dto.*;

public interface SymptomService {

    /**
     * 펫 이상징후 기록 생성
     * @param petId 이상징후 기록을 생성할 펫 id
     * @param createPetSymptomRequestDto {@link CreatePetSymptomRequestDto} 펫 이상징후 기록 생성 요청 DTO
     * @return {@link CreatePetSymptomResponseDto} 펫 이상징후 기록 생성 응답 DTO
     */
    CreatePetSymptomResponseDto createPetSymptom(Long petId, CreatePetSymptomRequestDto createPetSymptomRequestDto);

    /**
     * 펫 이상징후 기록 수정
     * @param symptomId 이상징후 기록을 수정할 이상징후 기록 id
     * @param modifyPetSymptomRequestDto {@link ModifyPetSymptomRequestDto} 펫 이상징후 기록 수정 요청 DTO
     * @return {@link ModifyPetSymptomResponseDto} 펫 이상징후 기록 수정 응답 DTO
     */
    ModifyPetSymptomResponseDto modifyPetSymptom(Long symptomId, ModifyPetSymptomRequestDto modifyPetSymptomRequestDto);

    /**
     * 펫 이상징후 기록 요청
     * @param petId 이상징후를 조회할 펫 id
     * @return {@link GetPetSymptomListResponseDto} 펫 이상징후 기록 요청 응답 DTO
     */
    GetPetSymptomListResponseDto getPetSymptomList(Long petId);
}
