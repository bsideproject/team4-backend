package com.bside.sidefriends.pet.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.pet.service.PetService;
import com.bside.sidefriends.pet.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@SideFriendsController
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/pets")
    public ResponseEntity<ResponseDto<CreatePetResponseDto>> createPet(@Valid @RequestBody CreatePetRequestDto createPetRequestDto) {
        CreatePetResponseDto createPetResponseDto = petService.createPet(createPetRequestDto);

        ResponseDto<CreatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_CREATE_SUCCESS, createPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<FindPetResponseDto>> findPet(@PathVariable("petId") Long petId) {
        FindPetResponseDto findPetResponseDto = petService.findPet(petId);

        ResponseDto<FindPetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_FIND_SUCCESS, findPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<ModifyPetResponseDto>> modifyPet(@PathVariable("petId") Long petId,
                                                                       @Valid @RequestBody ModifyPetRequestDto modifyPetRequestDto) {
        ModifyPetResponseDto modifyPetResponseDto = petService.modifyPet(petId, modifyPetRequestDto);

        ResponseDto<ModifyPetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_MODIFY_SUCCESS, modifyPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pets/{petId}/deactivate")
    public ResponseEntity<ResponseDto<DeactivatePetResponseDto>> deactivatePet(@PathVariable("petId") Long petId) {
        DeactivatePetResponseDto deactivatePetResponseDto = petService.deactivatePet(petId);

        ResponseDto<DeactivatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DEACTIVATE_SUCCESS, deactivatePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pets/{petId}/activate")
    public ResponseEntity<ResponseDto<ActivatePetResponseDto>> aactivatePet(@PathVariable("petId") Long petId) {
        ActivatePetResponseDto deactivatePetResponseDto = petService.activatePet(petId);

        ResponseDto<ActivatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DEACTIVATE_SUCCESS, deactivatePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<DeletePetResponseDto>> deletePet(@PathVariable("petId") Long petId) {
        DeletePetResponseDto deletePetResponseDto = petService.deletePet(petId);

        ResponseDto<DeletePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DELETE_SUCCESS, deletePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
