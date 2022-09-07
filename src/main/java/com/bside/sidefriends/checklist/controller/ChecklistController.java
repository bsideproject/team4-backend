package com.bside.sidefriends.checklist.controller;

import com.bside.sidefriends.checklist.service.ChecklistService;
import com.bside.sidefriends.checklist.service.dto.*;
import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@SideFriendsController
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @GetMapping("/checklist/list/{date}")
    public ResponseEntity<ResponseDto<FindChecklistResponseDto>> findChecklist(
            @LoginUser User user,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        System.out.println("ChecklistController findChecklist");

        FindChecklistResponseDto findChecklistResponseDto = checklistService.findChecklist(user, date);

        ResponseDto<FindChecklistResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_FIND_ALL_SUCCESS, findChecklistResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/checklist")
    public ResponseEntity<ResponseDto<CreateChecklistResponseDto>> createChecklist(
            @LoginUser User user,
            @Valid @RequestBody CreateChecklistRequestDto createChecklistRequestDto) {
        System.out.println("ChecklistController createChecklist");

        CreateChecklistResponseDto createChecklistResponseDto = checklistService.createChecklist(user, createChecklistRequestDto);

        ResponseDto<CreateChecklistResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_CREATE_SUCCESS, createChecklistResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/checklist/{checklistId}")
    public ResponseEntity<ResponseDto<FindChecklistByChecklistIdResponseDto>> findChecklistByChecklistId(
            @PathVariable("checklistId") Long checklistId) {
        System.out.println("ChecklistController findChecklistByChecklistId");

        FindChecklistByChecklistIdResponseDto findChecklistByChecklistIdResponseDto = checklistService.findChecklistByChecklistId(checklistId);

        ResponseDto<FindChecklistByChecklistIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_FIND_SUCCESS, findChecklistByChecklistIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/checklist/{checklistId}/date/{date}/modifyType/{modifyType}")
    public ResponseEntity<ResponseDto<ModifyChecklistResponseDto>> modifyChecklist(
            @PathVariable("checklistId") Long checklistId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Valid @RequestBody ModifyChecklistRequestDto modifyChecklistRequestDto,
            @PathVariable("modifyType") String modifyType) {
        ModifyChecklistResponseDto modifyChecklistResponseDto = checklistService.modifyChecklist(checklistId, date, modifyType, modifyChecklistRequestDto) ;

        ResponseDto<ModifyChecklistResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_MODIFY_SUCCESS, modifyChecklistResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/checklist/{checklistId}/date/{date}/deleteType/{deleteType}")
    public ResponseEntity<ResponseDto<DeleteChecklistResponseDto>> deleteChecklist(
            @PathVariable("checklistId") Long checklistId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @PathVariable("deleteType") String deleteType) {

        DeleteChecklistResponseDto deleteChecklistResponseDto = checklistService.deleteChecklist(checklistId, date, deleteType);

        ResponseDto<DeleteChecklistResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_DELETE_SUCCESS, deleteChecklistResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/checklist/checked/{checklistId}/date/{date}")
    public ResponseEntity<ResponseDto<ModifyCheckedResponseDto>> modifyChecked(
            @PathVariable("checklistId") Long checklistId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        ModifyCheckedResponseDto modifyCheckedResponseDto = checklistService.modifyChecked(checklistId, date);

        ResponseDto<ModifyCheckedResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CHECKLIST_CHECKED_SUCCESS, modifyCheckedResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
