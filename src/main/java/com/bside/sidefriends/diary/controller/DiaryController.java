package com.bside.sidefriends.diary.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.diary.service.DiaryService;
import com.bside.sidefriends.diary.service.dto.*;
import com.bside.sidefriends.security.mainOAuth2User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@Controller
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(code=510, message = "존재하지 않는 한줄일기입니다."),
        @ApiResponse(code=511, message = "자신이 작성한 한줄일기만 수정할 수 있습니다."),
        @ApiResponse(code=512, message = "자신이 작성한 한줄일기만 삭제할 수 있습니다."),
        @ApiResponse(code=513, message = "하루에 한 개의 한줄일기만 작성할 수 있습니다.")
})

public class DiaryController {

    private final DiaryService diaryService;

    @ApiResponses({
            @ApiResponse(code=501, message = "한줄일기 생성에 성공하였습니다. (200)")
    })
    @PostMapping("/pets/{petId}/diaries")
    ResponseEntity<ResponseDto<CreatePetDiaryResponseDto>> createPetDiary(@PathVariable("petId") Long petId,
                                                                          @Valid @RequestBody CreatePetDiaryRequestDto createPetDiaryRequestDto) {
        String username = getAuthenticatedUsername();

        CreatePetDiaryResponseDto createPetDiaryResponseDto = diaryService.createPetDiary(petId, username, createPetDiaryRequestDto);

        ResponseDto<CreatePetDiaryResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_CREATE_SUCCESS, createPetDiaryResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=502, message = "펫 모든 한줄일기 조회에 성공하였습니다. (200)")
    })
    @GetMapping("/pets/{petId}/diaries")
    ResponseEntity<ResponseDto<GetPetDiaryListResponseDto>> getPetDiaryList(@PathVariable("petId") Long petId) {

        GetPetDiaryListResponseDto getPetDiaryListResponseDto = diaryService.getPetDiaryList(petId);

        ResponseDto<GetPetDiaryListResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_FIND_ALL_SUCCESS, getPetDiaryListResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=503, message = "한줄일기 수정에 성공하였습니다. (200)")
    })
    @PutMapping("/pets/{petId}/diaries/{diaryId}")
    ResponseEntity<ResponseDto<ModifyPetDiaryResponseDto>> modifyPetDiary(@PathVariable("diaryId") Long diaryId,
                                                                          @Valid @RequestBody ModifyPetDiaryRequestDto modifyPetDiaryRequestDto) {

        String username = getAuthenticatedUsername();

        ModifyPetDiaryResponseDto modifyPetDiaryResponseDto = diaryService.modifyPetDiary(diaryId, username, modifyPetDiaryRequestDto);

        ResponseDto<ModifyPetDiaryResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.C_DIARY_MODIFY_SUCCESS, modifyPetDiaryResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }
    @ApiResponses({
            @ApiResponse(code=504, message = "한줄일기 삭제에 성공하였습니다. (200)")
    })
    @DeleteMapping("/pets/{petId}/diaries/{diaryId}")
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
