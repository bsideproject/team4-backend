package com.bside.sidefriends.quick.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateQuickResponseDto {

    @Getter
    private List<QuickDetail> quickDetailList;

    @Getter
    @AllArgsConstructor
    public static class QuickDetail {
        @ApiModelProperty(value = "퀵 Id", example = "1")
        private Long quickId;
        @ApiModelProperty(value = "퀵 제목", example = "퀵 제목")
        private String name;
        @ApiModelProperty(value = "퀵 실행횟수", example = "1")
        private int count;
        @ApiModelProperty(value = "퀵 총 실행횟수", example = "3")
        private int total;
        @ApiModelProperty(value = "퀵 설명", example = "퀵 설명")
        private String explanation;
        @ApiModelProperty(value = "퀵 순서", example = "4")
        private int order;
    }

}
