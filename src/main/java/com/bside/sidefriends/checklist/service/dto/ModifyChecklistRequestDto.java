package com.bside.sidefriends.checklist.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ModifyChecklistRequestDto {

    @ApiModelProperty(value = "할일 Id", example = "1")
    private Long checklistId;
    @ApiModelProperty(value = "할일 제목", example = "할일 제목")
    private String title;
    @ApiModelProperty(value = "할일 내용", example = "할일 내용")
    private String explanation;
    @ApiModelProperty(value = "할일 날짜", example = "2022-01-02")
    private LocalDate date;
    @ApiModelProperty(value = "할일 수행여부", example = "false")
    private boolean isDone;
    @ApiModelProperty(value = "할일 반복여부", example = "true")
    private boolean isRepeated;

    public boolean getIsDone() {
        return isDone;
    }
    public boolean getIsRepeated() {
        return isRepeated;
    }

    private RepeatDetail repeatDetail;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepeatDetail {

        @ApiModelProperty(value = "반복 주기", example = "weekly")
        private String eventPeriod;
        @ApiModelProperty(value = "반복 요일", example = "MONDAY")
        private String eventDate;
        @ApiModelProperty(value = "반복 월", example = "3")
        private String eventMonth;
        @ApiModelProperty(value = "반복 일", example = "25")
        private String eventDay;
        @ApiModelProperty(value = "반복 주차", example = "2")
        private String eventWeek;
        @ApiModelProperty(value = "반복 시작일", example = "2022-07-01")
        private LocalDate startedAt;
        @ApiModelProperty(value = "반복 종료일", example = "2022-12-31")
        private LocalDate endedAt;

    }
}
