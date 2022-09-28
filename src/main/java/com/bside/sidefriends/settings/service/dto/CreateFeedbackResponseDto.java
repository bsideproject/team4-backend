package com.bside.sidefriends.settings.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CreateFeedbackResponseDto {

    @ApiModelProperty(value = "피드백 전송 시간", example = "")
    private LocalDateTime dateTime;

}
