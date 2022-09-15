package com.bside.sidefriends.schedule.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindScheduleResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Getter
    private List<ScheduleDetail> scheduleDetailList;

    @Getter
    @AllArgsConstructor
    public static class ScheduleDetail {
        private Long scheduleId;
        private String title;
//        private String explanation;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;
        private LocalTime startTime;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;
        private LocalTime endTime;
        private boolean isAllDay;
//        private boolean isRepeated;
    }
}
