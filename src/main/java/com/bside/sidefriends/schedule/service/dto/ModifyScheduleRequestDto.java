package com.bside.sidefriends.schedule.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ModifyScheduleRequestDto {

    private Long scheduleId;
    private String title;
    private String explanation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private LocalTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
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

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startedAt;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endedAt;

    }
}
