package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.users.service.UserService;
import com.bside.sidefriends.users.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // FIXME: 실제 1차 서비스에서는 제공되지 않는 API
    @PostMapping("/users")
    public ResponseEntity<ResponseDto<CreateUserResponseDto>> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto createUserResponseDto = userService.createUser(userCreateRequestDto);

        ResponseDto<CreateUserResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_CREATE_SUCCESS, createUserResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<FindUserByUserIdResponseDto>> findUserByUserId(@PathVariable("userId") Long userId) {

        FindUserByUserIdResponseDto findUserByUserIdResponseDto = userService.findUserByUserId(userId);

        ResponseDto<FindUserByUserIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_FIND_SUCCESS, findUserByUserIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<ModifyUserResponseDto>> modifyUser(@PathVariable("userId") Long userId,
                                                            @Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {

        ModifyUserResponseDto modifyUserResponseDto = userService.modifyUser(userId, modifyUserRequestDto);

        ResponseDto<ModifyUserResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_MODIFY_SUCCESS, modifyUserResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<DeleteUserResponseDto>> deleteUser(@PathVariable("userId") Long userId) {

        DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(userId);

        ResponseDto<DeleteUserResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_DELETE_SUCCESS, deleteUserResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

}
