package com.bside.sidefriends.checklist.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteChecklistResponseDto {

    @ApiModelProperty(value = "할일 Id", example = "1")
    private Long checklistId;

}
