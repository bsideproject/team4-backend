package com.bside.sidefriends.schedule.service.dto;

import com.bside.sidefriends.quick.service.dto.FindQuickByPetIdResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindScheduleResponseDto {
    private LocalDate date;

    @Getter
    private List<ScheduleDetail> scheduleDetailList;

    @Getter
    @AllArgsConstructor
    public static class ScheduleDetail {
        private Long scheduleId;
        private String title;
//        private String explanation;
        private LocalDate startDate;
        private LocalTime startTime;
        private LocalDate endDate;
        private LocalTime endTime;
        private boolean isAllDay;
//        private boolean isRepeated;
    }
}
