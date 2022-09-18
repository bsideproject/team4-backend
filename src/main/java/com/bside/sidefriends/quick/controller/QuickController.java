package com.bside.sidefriends.quick.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.quick.service.QuickService;
import com.bside.sidefriends.quick.service.dto.*;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;

@SideFriendsController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(code=001, message = "입력 값이 올바르지 않습니다."),
        @ApiResponse(code=003, message = "허용되지 않은 요청 방법입니다."),
        @ApiResponse(code=311, message = "퀵 기록 서비스 입력 값이 올바르지 않습니다."),
        @ApiResponse(code=312, message = "존재하지 않는 퀵 기록입니다.")
})
public class QuickController {
    
    private final QuickService quickService;

    @ApiResponses({
            @ApiResponse(code=301, message = "퀵 기록 생성에 성공하였습니다. (200)"),
            @ApiResponse(code=302, message = "퀵 기록 생성에 실패하였습니다.")
    })
    @GetMapping("/quick")
    ResponseEntity<ResponseDto<CreateQuickResponseDto>> createDefaultQuick(@LoginUser User user) {

        CreateQuickResponseDto createQuickResponseDto = quickService.createDefaultQuick(user);

        ResponseDto<CreateQuickResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CREATE_QUICK_SUCCESS, createQuickResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=303, message = "퀵 기록 조회에 성공하였습니다. (200)"),
            @ApiResponse(code=304, message = "퀵 기록 조회에 실패하였습니다.")
    })
    @GetMapping("/quick/{date}")
    ResponseEntity<ResponseDto<FindQuickByPetIdResponseDto>> findQuick(
            @LoginUser User user,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) { // 2022-01-01

        FindQuickByPetIdResponseDto findQuickByPetIdResponseDto = quickService.findQuickByPetId(user, date);

        ResponseDto<FindQuickByPetIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.FIND_QUICK_SUCCESS, findQuickByPetIdResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @ApiResponses({
            @ApiResponse(code=305, message = "퀵 기록 수정에 성공하였습니다. (200)"),
            @ApiResponse(code=306, message = "퀵 기록 수정에 실패하였습니다.")
    })
    @PutMapping("/quick/{quickId}")
    ResponseEntity<ResponseDto<ChangeQuickResponseDto>> changeQuick(
            @PathVariable("quickId") Long quickId,
            @Valid @RequestBody ChangeQuickRequestDto changeQuickRequestDto) {

        ChangeQuickResponseDto changeQuickResponseDto = quickService.changeQuick(quickId, changeQuickRequestDto);

        ResponseDto<ChangeQuickResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_SUCCESS, changeQuickResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=307, message = "퀵 기록 순서 변경에 성공하였습니다. (200)"),
            @ApiResponse(code=308, message = "퀵 기록 순서 변경에 실패하였습니다.")
    })
    @PutMapping("/quick/order")
    ResponseEntity<ResponseDto<ChangeQuickOrderResponseDto>> changeQuickOrder (
            @LoginUser User user,
            @Valid @RequestBody ChangeQuickOrderRequestDto changeQuickOrderRequestDto) {

        ChangeQuickOrderResponseDto changeQuickOrderResponseDto = quickService.changeQuickOrder(user, changeQuickOrderRequestDto);

        ResponseDto<ChangeQuickOrderResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_ORDER_SUCCESS, changeQuickOrderResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiOperation(value = "changeQuickCount",
            notes = "(2022-09-18) URI 변경. 변경전 : /quick/count/{quickId} 변경 후 : /quick/count/{quickId}/{date}")
    @ApiResponses({
            @ApiResponse(code=309, message = "퀵 기록 실행 횟수 증가에 성공하였습니다. (200)"),
            @ApiResponse(code=310, message = "퀵 기록 실행 횟수 증가에 실패하였습니다.")
    })
    @PutMapping("/quick/count/{quickId}/{date}")
    ResponseEntity<ResponseDto<ChangeQuickCountResponseDto>> changeQuickCount (
            @PathVariable("quickId") Long quickId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        ChangeQuickCountResponseDto changeQuickCountResponseDto = quickService.changeQuickCount(quickId, date);

        ResponseDto<ChangeQuickCountResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_COUNT_SUCCESS, changeQuickCountResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
