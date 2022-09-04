package com.bside.sidefriends.pet.service;

import com.bside.sidefriends.pet.service.dto.*;
import com.bside.sidefriends.pet.service.dto.ActivatePetResponseDto;

public interface PetService {

    /**
     * 펫 정보 생성(최초 등록)
     * @param createPetRequestDto {@link CreatePetRequestDto} 펫 정보 생성 요청 DTO
     * @return {@link CreatePetResponseDto} 생성된 펫 정보 응답 DTO
     */
    CreatePetResponseDto createPet(CreatePetRequestDto createPetRequestDto);

    /**
     * 펫 상세 정보 조회
     * @param petId 조회할 펫 id
     * @return {@link FindPetResponseDto} 조회한 펫 정보 응답 DTO
     */
    FindPetResponseDto findPet(Long petId);

    /**
     * 펫 상세 정보 수정
     * @param petId 정보를 수정할 펫 id
     * @param modifyPetRequestDto {@link ModifyPetRequestDto} 펫 정보 수정 요청 DTO
     * @return {@link ModifyPetResponseDto} 수정한 펫 정보 응답 DTO
     */
    ModifyPetResponseDto modifyPet(Long petId, ModifyPetRequestDto modifyPetRequestDto);

    /**
     * 펫 기록 중지
     * @param petId 기록을 중지할 펫 id
     * @return {@link DeactivatePetResponseDto} 기록을 중지한 펫 정보 응답 DTO
     */
    DeactivatePetResponseDto deactivatePet(Long petId);

    /**
     * 펫 기록 활성화
     * @param petId 기록을 활성화할 펫 id
     * @return {@link ActivatePetResponseDto} 기록을 활성화한 펫 정보 응답 DTO
     */
    ActivatePetResponseDto activatePet(Long petId);


    /**
     * 펫 정보 삭제
     * @param petId 정보를 삭제할 펫 id
     * @return {@link DeletePetResponseDto} 펫 정보 삭제 응답 DTO
     */
    DeletePetResponseDto deletePet(Long petId);

    /**
     * TODO: 펫 연관 기능
     * - 펫 공유
     * - 사용자 펫 리스트
     * - 가족 펫 리스트
     * - 대표펫 설정
     */
}
