package com.bside.sidefriends.checklist.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindChecklistResponseDto {

    @ApiModelProperty(value = "조회할 날짜", example = "2022-07-01")
    private LocalDate date;

    @Getter
    private List<ChecklistDetail> checklistDetailList;

    @Getter
    @AllArgsConstructor
    public static class ChecklistDetail {

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

    }
}
