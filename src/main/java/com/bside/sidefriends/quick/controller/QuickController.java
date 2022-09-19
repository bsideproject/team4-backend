package com.bside.sidefriends.quick.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.quick.service.QuickService;
import com.bside.sidefriends.quick.service.dto.*;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
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
public class QuickController {
    
    private final QuickService quickService;
    
    @GetMapping("/quick")
    ResponseEntity<ResponseDto<CreateQuickResponseDto>> createDefaultQuick(Pet pet) {

        CreateQuickResponseDto createQuickResponseDto = quickService.createDefaultQuick(pet);

        ResponseDto<CreateQuickResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.CREATE_QUICK_SUCCESS, createQuickResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/quick/{date}")
    ResponseEntity<ResponseDto<FindQuickByPetIdResponseDto>> findQuick(
            @LoginUser User user,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate date) { // 2022-01-01

        FindQuickByPetIdResponseDto findQuickByPetIdResponseDto = quickService.findQuickByPetId(user, date);

        ResponseDto<FindQuickByPetIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.FIND_QUICK_SUCCESS, findQuickByPetIdResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @PutMapping("/quick/{quickId}")
    ResponseEntity<ResponseDto<ChangeQuickResponseDto>> changeQuick(
            @PathVariable("quickId") Long quickId,
            @Valid @RequestBody ChangeQuickRequestDto changeQuickRequestDto) {

        ChangeQuickResponseDto changeQuickResponseDto = quickService.changeQuick(quickId, changeQuickRequestDto);

        ResponseDto<ChangeQuickResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_SUCCESS, changeQuickResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/quick/order")
    ResponseEntity<ResponseDto<ChangeQuickOrderResponseDto>> changeQuickOrder (
            @LoginUser User user,
            @Valid @RequestBody ChangeQuickOrderRequestDto changeQuickOrderRequestDto) {

        ChangeQuickOrderResponseDto changeQuickOrderResponseDto = quickService.changeQuickOrder(user, changeQuickOrderRequestDto);

        ResponseDto<ChangeQuickOrderResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_ORDER_SUCCESS, changeQuickOrderResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/quick/count/{quickId}")
    ResponseEntity<ResponseDto<ChangeQuickCountResponseDto>> changeQuickCount (
            @PathVariable("quickId") Long quickId) {

        ChangeQuickCountResponseDto changeQuickCountResponseDto = quickService.changeQuickCount(quickId);

        ResponseDto<ChangeQuickCountResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.MODIFY_QUICK_COUNT_SUCCESS, changeQuickCountResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
