package com.bside.sidefriends.symptom.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.symptom.service.SymptomService;
import com.bside.sidefriends.symptom.service.dto.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@SideFriendsController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(code = 530, message = "존재하지 않는 이상징후 기록입니다."),
        @ApiResponse(code = 531, message = "이상징후 기록이 존재합니다. 수정을 이용해 주세요."),
        @ApiResponse(code = 532, message = "기록이 지원되지 않는 이상징후 항목입니다.")
})
public class SymptomController {

    private final SymptomService symptomService;

    @PostMapping("/pets/{petId}/symptoms")
    @ApiResponses({
            @ApiResponse(code = 520, message = "이상징후 기록 생성에 성공하였습니다. (200)")
    })
    public ResponseEntity<ResponseDto<CreatePetSymptomResponseDto>> createPetSymptom(@PathVariable("petId") Long petId,
                                                                                     @RequestBody @Valid CreatePetSymptomRequestDto createPetSymptomRequestDto) {

        CreatePetSymptomResponseDto createPetSymptomResponseDto = symptomService.createPetSymptom(petId, createPetSymptomRequestDto);

        ResponseDto<CreatePetSymptomResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_SYMPTOM_CREATE_SUCCESS, createPetSymptomResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/pets/{petId}/symptoms")
    @ApiResponses({
            @ApiResponse(code = 521, message = "이상징후 기록 조회에 성공하였습니다. (200)")
    })
    public ResponseEntity<ResponseDto<GetPetSymptomListResponseDto>> getPetSymptomList(@PathVariable("petId") Long petId,
                                                                                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        GetPetSymptomListResponseDto getPetSymptomListResponseDto = symptomService.getPetSymptomList(petId, date);

        ResponseDto<GetPetSymptomListResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_SYMPTOM_FIND_SUCCESS, getPetSymptomListResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pets/{petId}/symptoms/{symptomId}")
    @ApiResponses({
            @ApiResponse(code = 522, message = "이상징후 기록 수정에 성공하였습니다. (200)")
    })
    public ResponseEntity<ResponseDto<ModifyPetSymptomResponseDto>> modifyPetSymptom(@PathVariable Long symptomId,
                                                                                     @RequestBody @Valid ModifyPetSymptomRequestDto modifyPetSymptomRequestDto) {
        ModifyPetSymptomResponseDto modifyPetSymptomResponseDto = symptomService.modifyPetSymptom(symptomId, modifyPetSymptomRequestDto);

        ResponseDto<ModifyPetSymptomResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_MODIFY_SUCCESS, modifyPetSymptomResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }


}
