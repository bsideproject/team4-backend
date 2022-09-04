package com.bside.sidefriends.checklist.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ModifyChecklistResponseDto {

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
