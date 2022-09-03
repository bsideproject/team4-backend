package com.bside.sidefriends.checklist.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindChecklistResponseDto {

    private LocalDate date;

    @Getter
    private List<ChecklistDetail> checklistDetailList;

    @Getter
    @AllArgsConstructor
    public static class ChecklistDetail {
        private Long checklistId;
        private String title;
        private String explanation;
        private LocalDate date;
        private boolean isDone;
        private boolean isRepeated;

    }
}
