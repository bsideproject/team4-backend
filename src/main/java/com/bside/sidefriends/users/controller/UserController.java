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

    // FIXME: 1차 서비스에서 제공되지 않는 API. 시큐리티 회원가입 로직 작동 확인 완료 후 삭제 필요. IR.
    @PostMapping("/api/v1/users")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto userCreateResponseDto = userService.createUser(userCreateRequestDto);

        return ResponseEntity.ok().body(userCreateResponseDto);
    }

}
