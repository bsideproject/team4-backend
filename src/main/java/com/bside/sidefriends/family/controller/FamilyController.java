package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ResponseDto<CreateFamilyReponseDto>> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        CreateFamilyReponseDto createFamilyReponseDto = familyService.createFamily(createFamilyRequestDto);

        ResponseDto<CreateFamilyReponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_CREATE_SUCCESS, createFamilyReponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=602, message = "가족 그룹 조회에 성공하였습니다. (200)")
    })
    @GetMapping("/family/{familyId}")
    ResponseEntity<ResponseDto<FindFamilyMembersByFamilyIdResponseDto>> findFamily(@PathVariable("familyId") Long familyId) {

        FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyIdResponseDto
                = familyService.findFamilyMembersByFamilyId(familyId);

        ResponseDto<FindFamilyMembersByFamilyIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
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
            @ApiResponse(code=605, message = "가족 그룹 구성원 추가에 성공하였습니다. (200)")
    })
    @PostMapping("/family/{familyId}/members")
    ResponseEntity<ResponseDto<AddFamilyMemberResponseDto>> addFamilyMember(@PathVariable("familyId") Long familyId,
                                                               @Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        AddFamilyMemberResponseDto addFamilyMemberResponseDto
                = familyService.addFamilyMember(familyId, addFamilyMemberRequestDto);

        ResponseDto<AddFamilyMemberResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.F_ADD_MEMBER_SUCCESS, addFamilyMemberResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=606, message = "가족 그룹 구성원 삭제에 성공하였습니다. (200)")
    })
    @DeleteMapping("/family/{familyId}/members")
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
}
