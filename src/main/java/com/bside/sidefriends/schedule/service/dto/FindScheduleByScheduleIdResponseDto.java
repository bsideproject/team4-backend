package com.bside.sidefriends.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class FindScheduleByScheduleIdResponseDto {

    private Long scheduleId;
    private String title;
    private String explanation;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private boolean isAllDay;
    private boolean isRepeated;

    public boolean getIsAllDay() {
        return isAllDay;
    }
    public boolean getIsRepeated() {
        return isRepeated;
    }

    private RepeatDetail repeatDetail;

    @Getter
    @NoArgsConstructor
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
