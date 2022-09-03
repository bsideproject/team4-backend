package com.bside.sidefriends.checklist.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ModifyChecklistResponseDto {

    private Long checklistId;
    private String title;
    private String explanation;
    private LocalDate date;
    private boolean isDone;
    private boolean isRepeated;

}
