package com.bside.sidefriends.settings.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.settings.service.FeedbackService;
import com.bside.sidefriends.settings.service.dto.CreateFeedbackRequestDto;
import com.bside.sidefriends.settings.service.dto.CreateFeedbackResponseDto;
import com.bside.sidefriends.users.domain.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SideFriendsController
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @ApiResponses({
            @ApiResponse(code=700, message = "피드백 전송에 성공하였습니다. (200)")
    })
    @PostMapping("/feedback")
    public ResponseEntity<ResponseDto<CreateFeedbackResponseDto>> createFeedback(
            @LoginUser User user,
            @RequestBody CreateFeedbackRequestDto createFeedbackRequestDto) {

        CreateFeedbackResponseDto createFeedbackResponseDto = feedbackService.createFeedback(user, createFeedbackRequestDto);

        ResponseDto<CreateFeedbackResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.FEEDBACK_CREATE_SUCCESS, createFeedbackResponseDto);

        return ResponseEntity.ok().body(responseDto);


    }
}
