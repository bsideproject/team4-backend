package com.bside.sidefriends.quick.service.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ChangeQuickResponseDto {

    @ApiModelProperty(value = "퀵 Id", example = "1")
    private Long quickId;
    @ApiModelProperty(value = "퀵 제목", example = "퀵 제목")
    private String name;
    @ApiModelProperty(value = "퀵 총 실행횟수", example = "3")
    private int total;
    @ApiModelProperty(value = "퀵 설명", example = "퀵 설명")
    private String explanation;
}
