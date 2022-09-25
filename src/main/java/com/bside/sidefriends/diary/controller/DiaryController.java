package com.bside.sidefriends.diary.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.diary.service.DiaryService;
import com.bside.sidefriends.diary.service.dto.*;
import com.bside.sidefriends.security.mainOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/pet/{petId}/diaries")
    ResponseEntity<ResponseDto<CreatePetDiaryResponseDto>> createPetDiary(@PathVariable("petId") Long petId,
                                                                          @Valid @RequestBody CreatePetDiaryRequestDto createPetDiaryRequestDto) {
        String username = getAuthenticatedUsername();

        CreatePetDiaryResponseDto createPetDiaryResponseDto = diaryService.createPetDiary(petId, username, createPetDiaryRequestDto);

        ResponseDto<CreatePetDiaryResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_CREATE_SUCCESS, createPetDiaryResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/pet/{petId}/diaries")
    ResponseEntity<ResponseDto<GetPetDiaryListResponseDto>> getPetDiaryList(@PathVariable("petId") Long petId) {

        GetPetDiaryListResponseDto getPetDiaryListResponseDto = diaryService.getPetDiaryList(petId);

        ResponseDto<GetPetDiaryListResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_FIND_ALL_SUCCESS, getPetDiaryListResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/pet/{petId}/diaries/{diaryId}")
    ResponseEntity<ResponseDto<ModifyPetDiaryResponseDto>> modifyPetDiary(@PathVariable("diaryId") Long diaryId,
                                                                          @Valid @RequestBody ModifyPetDiaryRequestDto modifyPetDiaryRequestDto) {

        String username = getAuthenticatedUsername();

        ModifyPetDiaryResponseDto modifyPetDiaryResponseDto = diaryService.modifyPetDiary(diaryId, username, modifyPetDiaryRequestDto);

        ResponseDto<ModifyPetDiaryResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_MODIFY_SUCCESS, modifyPetDiaryResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/pet/{petId}/diaries/{diaryId}")
    ResponseEntity<ResponseDto<DeletePetDiaryResponseDto>> deletePetDiary(@PathVariable("diaryId") Long diaryId) {

        String username = getAuthenticatedUsername();

        DeletePetDiaryResponseDto deletePetDiaryResponseDto = diaryService.deletePetDiary(diaryId, username);

        ResponseDto<DeletePetDiaryResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_DELETE_SUCCESS, deletePetDiaryResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    // TODO: 컨트롤러에 private 메서드 두어도 될지 고민
    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((mainOAuth2User) principal).getUsername();
    }

}
