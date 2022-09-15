package com.bside.sidefriends.schedule.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private LocalTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private LocalTime endTime;

    private boolean isAllDay;
    private boolean isRepeated;

}
