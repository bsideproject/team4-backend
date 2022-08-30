package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/family")
    ResponseEntity<ResponseDto<CreateFamilyReponseDto>> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        CreateFamilyReponseDto createFamilyReponseDto = familyService.createFamily(createFamilyRequestDto);

        ResponseDto<CreateFamilyReponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_CREATE_SUCCESS, createFamilyReponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/family")
    ResponseEntity<ResponseDto<FindFamilyMembersByFamilyIdResponseDto>> findFamily(@LoginUser User user) {

        // TOOD: 예외 처리 변경
        Long familyId = user.getFamilyId();

        FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyIdResponseDto
                = familyService.findFamilyMembersByFamilyId(familyId);

        ResponseDto<FindFamilyMembersByFamilyIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_FIND_SUCCESS, findFamilyMembersByFamilyIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family")
    ResponseEntity<ResponseDto<DeleteFamilyResponseDto>> deleteFamily(@LoginUser User user) {

        // TODO: 예외 처리 변경(가족 그룹 존재 여부, 가족 그룹장 여부)
        Long familyId = user.getFamilyId();

        DeleteFamilyResponseDto deleteFamilyResponseDto = familyService.deleteFamily(familyId);

        ResponseDto<DeleteFamilyResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_SUCCESS, deleteFamilyResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @PostMapping("/family/members")
    ResponseEntity<ResponseDto<AddFamilyMemberResponseDto>> addFamilyMember(@LoginUser User user,
                                                               @Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        // TODO: 예외 처리 변경
        Long familyId = user.getFamilyId();

        AddFamilyMemberResponseDto addFamilyMemberResponseDto
                = familyService.addFamilyMember(familyId, addFamilyMemberRequestDto);

        ResponseDto<AddFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_ADD_MEMBER_SUCCESS, addFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family/members")
    ResponseEntity<ResponseDto<DeleteFamilyMemberResponseDto>> deleteFamilyMember(@LoginUser User user,
                                                                     @Valid @RequestBody DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {

        // TODO: 예외 처리 변경(가족 그룹 존재 여부, 가족 그룹장 여부)
        Long familyId = user.getFamilyId();

        DeleteFamilyMemberResponseDto deleteFamilyMemberResponseDto
                = familyService.deleteFamilyMember(familyId, deleteFamilyMemberRequestDto);

        ResponseDto<DeleteFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_MEMBER_SUCCESS, deleteFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/family/manager")
    ResponseEntity<ResponseDto<ChangeFamilyManagerResponseDto>> changeFamilyManager(@LoginUser User user,
                                                                       @Valid @RequestBody ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {

        // TODO: 예외 처리 변경(가족 그룹 존재 여부, 가족 그룹장 여부)
        Long familyId = user.getFamilyId();

        ChangeFamilyManagerResponseDto changeFamilyManagerResponseDto
                = familyService.changeFamilyManager(familyId, changeFamilyManagerRequestDto);

        ResponseDto<ChangeFamilyManagerResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_MODIFY_MANAGER_SUCCESS, changeFamilyManagerResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }
}
