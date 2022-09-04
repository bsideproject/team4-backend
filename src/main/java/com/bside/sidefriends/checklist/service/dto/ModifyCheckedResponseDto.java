package com.bside.sidefriends.checklist.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyCheckedResponseDto {

    private Long checklistId;
    private boolean isDone;

}
