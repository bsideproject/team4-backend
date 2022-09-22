package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.security.mainOAuth2User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(code=001, message = "입력 값이 올바르지 않습니다."),
        @ApiResponse(code=003, message = "허용되지 않은 요청 방법입니다."),
        @ApiResponse(code=610, message = "가족 서비스 입력 값이 올바르지 않습니다."),
        @ApiResponse(code=611, message = "존재하지 않는 가족 그룹입니다."),
        @ApiResponse(code=612, message = "이미 삭제된 가족 그룹입니다."),
        @ApiResponse(code=613, message = "가족 그룹 정원을 초과하였습니다."),
        @ApiResponse(code=614, message = "가족 그룹을 삭제할 수 없습니다."),
        @ApiResponse(code=615, message = "가족 그룹 구성원을 추방할 수 없습니다."),
        @ApiResponse(code=616, message = "가족 그룹에 속해 있는 사용자입니다."),
        @ApiResponse(code=617, message = "가족 그룹에 속해 있지 않은 사용자입니다."),
        @ApiResponse(code=618, message = "가족 그룹 구성원이 존재하지 않습니다."),
        @ApiResponse(code=619, message = "가족 그룹 그룹장이 존재하지 않습니다."),
        @ApiResponse(code=620, message = "가족 그룹장 권한이 있어야 합니다."),
})
public class FamilyController {

    private final FamilyService familyService;

    @ApiResponses({
            @ApiResponse(code=601, message = "가족 그룹 생성에 성공하였습니다. (200)")
    })
    @PostMapping("/family")
    ResponseEntity<ResponseDto<CreateFamilyResponseDto>> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        /**
         * NOTE: 가족 생성 요청 시 그룹장과 그룹원 식별 방법
         * - 그룹원이 그룹장으로부터 받은 초대 링크를 수락할 때 타게 되는 POST 요청 API
         * - 그룹장 권한 필요 없음
         * - 그룹장: 회원 id로 식별. 요청 body 데이터에 보냄
         * - 그룹원: authorization 헤더로 식별
         * IR
         */
        String groupMemberUsername = getAuthenticatedUsername();
        CreateFamilyResponseDto createFamilyResponseDto = familyService.createFamily(groupMemberUsername, createFamilyRequestDto);

        ResponseDto<CreateFamilyResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_CREATE_SUCCESS, createFamilyResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/family/members")
    ResponseEntity<ResponseDto<AddFamilyMemberResponseDto>> addFamilyMember(@Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        /**
         * NOTE: 가족 구성원 추가 요청 시 그룹장과 그룹원 식별 방법
         * - 이미 있는 가족 그룹이라는 것 외에, 가족 그룹 생성 로직과 크게 다르지 않음
         * - API 접근 자체에 그룹장 권한은 필요 없으나, 비즈니스 로직 상에서 그룹장 검사 필요
         * - 그룹원이 그룹장으로부터 받은 초대 링크를 수락할 때 타게 되는 POST 요청 API
         * - 그룹장: 회원 id로 식별. 요청 body 데이터에 보냄
         * - 그룹원: authorization 헤더로 식별
         * IR
         */

        String groupMemberUsername = getAuthenticatedUsername();
        AddFamilyMemberResponseDto addFamilyMemberResponseDto = familyService.addFamilyMember(groupMemberUsername, addFamilyMemberRequestDto);

        ResponseDto<AddFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_ADD_MEMBER_SUCCESS, addFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=602, message = "가족 그룹 조회에 성공하였습니다. (200)")
    })
    @GetMapping("/family/{familyId}")
    ResponseEntity<ResponseDto<FindFamilyMembersResponseDto>> findFamily(@PathVariable("familyId") Long familyId) {

        FindFamilyMembersResponseDto findFamilyMembersByFamilyIdResponseDto = familyService.findFamilyMembers(familyId);

        ResponseDto<FindFamilyMembersResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_FIND_SUCCESS, findFamilyMembersByFamilyIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=603, message = "가족 그룹 삭제에 성공하였습니다. (200)")
    })
    @DeleteMapping("/family/{familyId}")
    ResponseEntity<ResponseDto<DeleteFamilyResponseDto>> deleteFamily(@PathVariable("familyId") Long familyId) {

        DeleteFamilyResponseDto deleteFamilyResponseDto = familyService.deleteFamily(familyId);

        ResponseDto<DeleteFamilyResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_SUCCESS, deleteFamilyResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }



    @ApiResponses({
            @ApiResponse(code=606, message = "가족 그룹 구성원 삭제에 성공하였습니다. (200)")
    })
    @PostMapping("/family/{familyId}/members")
    ResponseEntity<ResponseDto<DeleteFamilyMemberResponseDto>> deleteFamilyMember(@PathVariable("familyId") Long familyId,
                                                                     @Valid @RequestBody DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {
        DeleteFamilyMemberResponseDto deleteFamilyMemberResponseDto
                = familyService.deleteFamilyMember(familyId, deleteFamilyMemberRequestDto);

        ResponseDto<DeleteFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_DELETE_MEMBER_SUCCESS, deleteFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=604, message = "가족 그룹장 변경에 성공하였습니다. (200)")
    })
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
