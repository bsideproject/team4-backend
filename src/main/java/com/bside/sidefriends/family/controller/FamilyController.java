package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.error.exception.FamilyManagerRequiredException;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.security.auth.LoginUsername;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    @GetMapping("/family")
    ResponseEntity<ResponseDto<FindFamilyMembersResponseDto>> findFamily(@LoginUsername String username) {

        FindFamilyMembersResponseDto findFamilyMembersByFamilyIdResponseDto = familyService.findFamilyMembers(username);

        ResponseDto<FindFamilyMembersResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_FIND_SUCCESS, findFamilyMembersByFamilyIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family")
    ResponseEntity<ResponseDto<DeleteFamilyResponseDto>> deleteFamily(@LoginUsername String username) {

        // 권한 검사
        if (!userService.findUser(username).getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException("가족 그룹장만이 가족 그룹을 삭제할 수 있습니다.");
        }

        DeleteFamilyResponseDto deleteFamilyResponseDto = familyService.deleteFamily(username);

        ResponseDto<DeleteFamilyResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_SUCCESS, deleteFamilyResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @PostMapping("/family/members")
    ResponseEntity<ResponseDto<AddFamilyMemberResponseDto>> addFamilyMember(@LoginUsername String username,
                                                               @Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        AddFamilyMemberResponseDto addFamilyMemberResponseDto = familyService.addFamilyMember(username, addFamilyMemberRequestDto);

        ResponseDto<AddFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_ADD_MEMBER_SUCCESS, addFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/family/members")
    ResponseEntity<ResponseDto<DeleteFamilyMemberResponseDto>> deleteFamilyMember(@LoginUsername String username,
                                                                     @Valid @RequestBody DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {

        if (!userService.findUser(username).getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException("가족 그룹장만이 가족 구성원을 삭제할 수 있습니다.");
        }

        DeleteFamilyMemberResponseDto deleteFamilyMemberResponseDto
                = familyService.deleteFamilyMember(username, deleteFamilyMemberRequestDto);

        ResponseDto<DeleteFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_MEMBER_SUCCESS, deleteFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/family/manager")
    ResponseEntity<ResponseDto<ChangeFamilyManagerResponseDto>> changeFamilyManager(@LoginUsername String username,
                                                                       @Valid @RequestBody ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {

        if (!userService.findUser(username).getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException("가족 그룹장만이 가족 그룹장 권한을 이양할 수 있습니다.");
        }

        ChangeFamilyManagerResponseDto changeFamilyManagerResponseDto = familyService.changeFamilyManager(username, changeFamilyManagerRequestDto);

        ResponseDto<ChangeFamilyManagerResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_MODIFY_MANAGER_SUCCESS, changeFamilyManagerResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    // FIXME: 1차에서 제공되지 않는 API
    @PostMapping("/family")
    ResponseEntity<ResponseDto<CreateFamilyReponseDto>> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        CreateFamilyReponseDto createFamilyReponseDto = familyService.createFamily(createFamilyRequestDto);

        ResponseDto<CreateFamilyReponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_CREATE_SUCCESS, createFamilyReponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
