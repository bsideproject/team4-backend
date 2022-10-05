package com.bside.sidefriends.settings.service;

import com.bside.sidefriends.settings.service.dto.CreateFeedbackRequestDto;
import com.bside.sidefriends.settings.service.dto.CreateFeedbackResponseDto;
import com.bside.sidefriends.users.domain.User;

public interface FeedbackService {

    /**
     * 피드백 작성
     * @param user 피드백을 작성한 User
     * @param createFeedbackRequestDto 피드백 생성 요청 DTO
     * @return
     */
    CreateFeedbackResponseDto createFeedback(User user, CreateFeedbackRequestDto createFeedbackRequestDto);
}
