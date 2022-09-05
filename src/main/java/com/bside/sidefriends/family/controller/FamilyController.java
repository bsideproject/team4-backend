package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.security.mainOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/family")
    ResponseEntity<ResponseDto<CreateFamilyReponseDto>> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        /**
         * NOTE: 가족 생성 요청 시 그룹장과 그룹원 식별 방법
         * - 그룹원이 그룹장으로부터 받은 초대 링크를 클릭할 때 타게 되는 POST 요청 API
         * - 그룹장: 회원 id로 식별. 요청 body 데이터에 보냄
         * - 그룹원: authorization 헤더로 식별
         * IR
         */
        String groupMemberUsername = getAuthenticatedUsername();
        CreateFamilyReponseDto createFamilyReponseDto = familyService.createFamily(groupMemberUsername, createFamilyRequestDto);

        ResponseDto<CreateFamilyReponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_CREATE_SUCCESS, createFamilyReponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/family/{familyId}")
    ResponseEntity<ResponseDto<FindFamilyMembersByFamilyIdResponseDto>> findFamily(@PathVariable("familyId") Long familyId) {

        FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyIdResponseDto
                = familyService.findFamilyMembersByFamilyId(familyId);

        ResponseDto<FindFamilyMembersByFamilyIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_FIND_SUCCESS, findFamilyMembersByFamilyIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family/{familyId}")
    ResponseEntity<ResponseDto<DeleteFamilyResponseDto>> deleteFamily(@PathVariable("familyId") Long familyId) {

        DeleteFamilyResponseDto deleteFamilyResponseDto = familyService.deleteFamily(familyId);

        ResponseDto<DeleteFamilyResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_SUCCESS, deleteFamilyResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @PostMapping("/family/{familyId}/members")
    ResponseEntity<ResponseDto<AddFamilyMemberResponseDto>> addFamilyMember(@PathVariable("familyId") Long familyId,
                                                               @Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        AddFamilyMemberResponseDto addFamilyMemberResponseDto
                = familyService.addFamilyMember(familyId, addFamilyMemberRequestDto);

        ResponseDto<AddFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_ADD_MEMBER_SUCCESS, addFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family/{familyId}/members")
    ResponseEntity<ResponseDto<DeleteFamilyMemberResponseDto>> deleteFamilyMember(@PathVariable("familyId") Long familyId,
                                                                     @Valid @RequestBody DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {
        DeleteFamilyMemberResponseDto deleteFamilyMemberResponseDto
                = familyService.deleteFamilyMember(familyId, deleteFamilyMemberRequestDto);

        ResponseDto<DeleteFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_MEMBER_SUCCESS, deleteFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/family/{familyId}/manager")
    ResponseEntity<ResponseDto<ChangeFamilyManagerResponseDto>> changeFamilyManager(@PathVariable("familyId") Long familyId,
                                                                       @Valid @RequestBody ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {
        ChangeFamilyManagerResponseDto changeFamilyManagerResponseDto
                = familyService.changeFamilyManager(familyId, changeFamilyManagerRequestDto);

        ResponseDto<ChangeFamilyManagerResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_MODIFY_MANAGER_SUCCESS, changeFamilyManagerResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((mainOAuth2User) principal).getUsername();
    }
}
