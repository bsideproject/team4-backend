package com.bside.sidefriends.checklist.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyCheckedResponseDto {

    @ApiModelProperty(value = "할일 Id", example = "1")
    private Long checklistId;
    @ApiModelProperty(value = "할일 수행여부", example = "false")
    private boolean isDone;

}
