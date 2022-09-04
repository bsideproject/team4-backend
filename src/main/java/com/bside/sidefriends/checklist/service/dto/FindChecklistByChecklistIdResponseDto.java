package com.bside.sidefriends.checklist.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FindChecklistByChecklistIdResponseDto {

    private Long checklistId;
    private String title;
    private String explanation;
    private LocalDate date;
    private boolean isDone;
    private boolean isRepeated;

    public boolean getIsDone() {
        return isDone;
    }
    public boolean getIsRepeated() {
        return isRepeated;
    }

    private RepeatDetail repeatDetail;

    @Getter
    @AllArgsConstructor
    public static class RepeatDetail {

        private String eventPeriod;
        private String eventDate;
        private String eventMonth;
        private String eventDay;
        private String eventWeek;
        private LocalDate startedAt;
        private LocalDate endedAt;

    }
}
