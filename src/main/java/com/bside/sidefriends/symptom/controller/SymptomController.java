package com.bside.sidefriends.symptom.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.symptom.service.SymptomService;
import com.bside.sidefriends.symptom.service.dto.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@SideFriendsController
@RequiredArgsConstructor
public class SymptomController {

    private final SymptomService symptomService;

    @PostMapping("/pet/{petId}/symptoms")
    public ResponseEntity<ResponseDto<CreatePetSymptomResponseDto>> createPetSymptoms(@PathVariable("petId") Long petId,
                                                                                     @RequestBody @Valid CreatePetSymptomRequestDto createPetSymptomRequestDto) {

        CreatePetSymptomResponseDto createPetSymptomResponseDto = symptomService.createPetSymptom(petId, createPetSymptomRequestDto);

        ResponseDto<CreatePetSymptomResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_SYMPTOM_CREATE_SUCCESS, createPetSymptomResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/pet/{petId}/symptoms")
    public ResponseEntity<ResponseDto<GetPetSymptomListResponseDto>> getPetSymptomList(@PathVariable("petId") Long petId,
                                                                                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        GetPetSymptomListResponseDto getPetSymptomListResponseDto = symptomService.getPetSymptomList(petId, date);

        ResponseDto<GetPetSymptomListResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_SYMPTOM_FIND_SUCCESS, getPetSymptomListResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pet/{petId}/symptoms/{symptomId}")
    public ResponseEntity<ResponseDto<ModifyPetSymptomResponseDto>> modifyPetSymptom(@PathVariable Long symptomId,
                                                                                     @RequestBody @Valid ModifyPetSymptomRequestDto modifyPetSymptomRequestDto) {
        ModifyPetSymptomResponseDto modifyPetSymptomResponseDto = symptomService.modifyPetSymptom(symptomId, modifyPetSymptomRequestDto);

        ResponseDto<ModifyPetSymptomResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_MODIFY_SUCCESS, modifyPetSymptomResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }


}
