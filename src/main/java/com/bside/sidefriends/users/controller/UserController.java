package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.users.service.UserService;
import com.bside.sidefriends.users.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto userCreateResponseDto = userService.createUser(userCreateRequestDto);

        return ResponseEntity.ok().body(userCreateResponseDto);
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<FindUserByUserIdResponseDto> findUserByUserId(@PathVariable("userId") Long userId) {

        FindUserByUserIdResponseDto findUserByUserIdResponseDto = userService.findUserByUserId(userId);

        return ResponseEntity.ok().body(findUserByUserIdResponseDto);
    }

    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<ModifyUserResponseDto> modifyUser(@PathVariable("userId") Long userId,
                                                            @Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {

        ModifyUserResponseDto modifyUserResponseDto = userService.modifyUser(userId, modifyUserRequestDto);

        return ResponseEntity.ok().body(modifyUserResponseDto);
    }


}
