package com.bside.sidefriends.quick.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ChangeQuickRequestDto {

    @ApiModelProperty(value = "퀵 제목", example = "퀵 제목")
    @NotNull
    private String name;

    @ApiModelProperty(value = "퀵 총 실행횟수", example = "3")
    @NotNull
    private int total;

    @ApiModelProperty(value = "퀵 설명", example = "퀵 설명")
    private String explanation;
}
