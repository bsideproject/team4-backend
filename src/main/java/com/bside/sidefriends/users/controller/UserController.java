package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.security.mainOAuth2User;
import com.bside.sidefriends.users.service.UserService;
import com.bside.sidefriends.users.service.dto.*;
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
        @ApiResponse(code=160, message = "회원 서비스 입력 값이 올바르지 않습니다."),
        @ApiResponse(code=161, message = "존재하지 않는 회원입니다."),
        @ApiResponse(code=162, message = "이미 존재하는 회원입니다."),
        @ApiResponse(code=163, message = "이미 삭제된 회원입니다."),
        @ApiResponse(code=164, message = "수정하려는 회원 정보가 동일합니다."),
        @ApiResponse(code=165, message = "회원을 삭제할 수 없습니다."),
        @ApiResponse(code=173, message = "가족 그룹장 권한을 가지고 있는 회원입니다.")
})
public class UserController {

    private final UserService userService;

    @ApiResponses({
            @ApiResponse(code=152, message = "회원 조회에 성공하였습니다. (200)")
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<FindUserByUserIdResponseDto>> findUserByUserId() {

        String username = getAuthenticatedUsername();
        FindUserByUserIdResponseDto findUserByUserIdResponseDto = userService.findUserByUserId(username);

        ResponseDto<FindUserByUserIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_FIND_SUCCESS, findUserByUserIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=153, message = "회원 정보 수정에 성공하였습니다. (200)")
    })
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<ModifyUserResponseDto>> modifyUser(@Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {

        String username = getAuthenticatedUsername();
        ModifyUserResponseDto modifyUserResponseDto = userService.modifyUser(username, modifyUserRequestDto);

        ResponseDto<ModifyUserResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_MODIFY_SUCCESS, modifyUserResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=154, message = "회원 삭제에 성공하였습니다. (200)")
    })
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<DeleteUserResponseDto>> deleteUser() {

        String username = getAuthenticatedUsername();
        DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(username);

        ResponseDto<DeleteUserResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_DELETE_SUCCESS, deleteUserResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=151, message = "회원 가입에 성공하였습니다. (200)")
    })
    // FIXME: 1차 서비스에서 제공되지 않는 API. 시큐리티 회원가입 로직 작동 확인 완료 후 삭제 필요. IR.
    @PostMapping("/api/v1/users")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto userCreateResponseDto = userService.createUser(userCreateRequestDto);

        return ResponseEntity.ok().body(userCreateResponseDto);
    }

    // TODO: 컨트롤러에 private 메서드 두어도 될지 고민
    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((mainOAuth2User) principal).getUsername();
    }

}
