package com.bside.sidefriends.pet.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.pet.service.PetService;
import com.bside.sidefriends.pet.service.dto.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@SideFriendsController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(code=001, message = "입력 값이 올바르지 않습니다."),
        @ApiResponse(code=003, message = "허용되지 않은 요청 방법입니다.")
})
public class PetController {

    private final PetService petService;

    @ApiResponses({
            @ApiResponse(code=201, message = "펫 생성에 성공하였습니다. (200)")
    })
    @PostMapping("/pets")
    public ResponseEntity<ResponseDto<CreatePetResponseDto>> createPet(@Valid @RequestBody CreatePetRequestDto createPetRequestDto) {
        CreatePetResponseDto createPetResponseDto = petService.createPet(createPetRequestDto);

        ResponseDto<CreatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_CREATE_SUCCESS, createPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=202, message = "펫 조회에 성공하였습니다. (200)")
    })
    @GetMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<FindPetResponseDto>> findPet(@PathVariable("petId") Long petId) {
        FindPetResponseDto findPetResponseDto = petService.findPet(petId);

        ResponseDto<FindPetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_FIND_SUCCESS, findPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=203, message = "펫 정보 수정에 성공하였습니다. (200)")
    })
    @PutMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<ModifyPetResponseDto>> modifyPet(@PathVariable("petId") Long petId,
                                                                       @Valid @RequestBody ModifyPetRequestDto modifyPetRequestDto) {
        ModifyPetResponseDto modifyPetResponseDto = petService.modifyPet(petId, modifyPetRequestDto);

        ResponseDto<ModifyPetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_MODIFY_SUCCESS, modifyPetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=204, message = "펫 기록 중지에 성공하였습니다. (200)")
    })
    @PutMapping("/pets/{petId}/deactivate")
    public ResponseEntity<ResponseDto<DeactivatePetResponseDto>> deactivatePet(@PathVariable("petId") Long petId) {
        DeactivatePetResponseDto deactivatePetResponseDto = petService.deactivatePet(petId);

        ResponseDto<DeactivatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DEACTIVATE_SUCCESS, deactivatePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=205, message = "펫 기록 활성화에 성공하였습니다. (200)")
    })
    @PutMapping("/pets/{petId}/activate")
    public ResponseEntity<ResponseDto<ActivatePetResponseDto>> aactivatePet(@PathVariable("petId") Long petId) {
        ActivatePetResponseDto deactivatePetResponseDto = petService.activatePet(petId);

        ResponseDto<ActivatePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DEACTIVATE_SUCCESS, deactivatePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=206, message = "펫 삭제에 성공하였습니다. (200)")
    })
    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<ResponseDto<DeletePetResponseDto>> deletePet(@PathVariable("petId") Long petId) {
        DeletePetResponseDto deletePetResponseDto = petService.deletePet(petId);

        ResponseDto<DeletePetResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.P_DELETE_SUCCESS, deletePetResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
