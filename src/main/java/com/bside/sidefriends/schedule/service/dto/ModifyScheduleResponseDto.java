package com.bside.sidefriends.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ModifyScheduleResponseDto {

    private Long scheduleId;
    private String title;
    private String explanation;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private boolean isAllDay;
    private boolean isRepeated;

}
