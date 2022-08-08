package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.users.service.UserService;
import com.bside.sidefriends.users.service.dto.CreateUserRequestDto;
import com.bside.sidefriends.users.service.dto.CreateUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userCreateRequestDto) {

        CreateUserResponseDto userCreateResponseDto = userService.createUser(userCreateRequestDto);

        // TODO: 응답 형식 분리. 2022.07.31. IR
        return ResponseEntity.ok().body(userCreateResponseDto);
    }


}
