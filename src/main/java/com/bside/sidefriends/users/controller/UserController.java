package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
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

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto userCreateResponseDto = userService.createUser(userCreateRequestDto);

        return ResponseEntity.ok().body(userCreateResponseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<FindUserByUserIdResponseDto> findUserByUserId(@PathVariable("userId") Long userId) {

        FindUserByUserIdResponseDto findUserByUserIdResponseDto = userService.findUserByUserId(userId);

        return ResponseEntity.ok().body(findUserByUserIdResponseDto);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ModifyUserResponseDto> modifyUser(@PathVariable("userId") Long userId,
                                                            @Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {

        ModifyUserResponseDto modifyUserResponseDto = userService.modifyUser(userId, modifyUserRequestDto);

        return ResponseEntity.ok().body(modifyUserResponseDto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<DeleteUserResponseDto> deleteUser(@PathVariable("userId") Long userId) {

        DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(userId);

        return ResponseEntity.ok().body(deleteUserResponseDto);
    }

}
