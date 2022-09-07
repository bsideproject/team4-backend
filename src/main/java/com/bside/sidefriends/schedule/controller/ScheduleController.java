package com.bside.sidefriends.schedule.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.schedule.service.ScheduleService;
import com.bside.sidefriends.schedule.service.dto.*;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(tags = {"schedule-controller (Deprecated?!)"})
@SideFriendsController
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @GetMapping("/schedule/list/{date}")
    public ResponseEntity<ResponseDto<FindScheduleResponseDto>> findSchedule(
            @LoginUser User user,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        System.out.println("ScheduleController findSchedule");

        FindScheduleResponseDto findScheduleResponseDto = scheduleService.findSchedule(user, date);

        ResponseDto<FindScheduleResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.SCHEDULE_FIND_ALL_SUCCESS, findScheduleResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto<CreateScheduleResponseDto>> createSchedule(
            @LoginUser User user,
            @Valid @RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
        System.out.println("ScheduleController createSchedule");

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(user, createScheduleRequestDto);

        ResponseDto<CreateScheduleResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.SCHEDULE_CREATE_SUCCESS, createScheduleResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseDto<FindScheduleByScheduleIdResponseDto>> findScheduleByScheduleId(
            @PathVariable("scheduleId") Long scheduleId) {
        System.out.println("ScheduleController findScheduleByScheduleId");

        FindScheduleByScheduleIdResponseDto findScheduleByScheduleIdResponseDto = scheduleService.findScheduleByScheduleId(scheduleId);

        ResponseDto<FindScheduleByScheduleIdResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.SCHEDULE_FIND_SUCCESS, findScheduleByScheduleIdResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }
    
    @PutMapping("/schedule/{scheduleId}/date/{date}/modifyType/{modifyType}")
    public ResponseEntity<ResponseDto<ModifyScheduleResponseDto>> modifySchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Valid @RequestBody ModifyScheduleRequestDto modifyScheduleRequestDto,
            @PathVariable("modifyType") String modifyType) {
        ModifyScheduleResponseDto modifyScheduleResponseDto = scheduleService.modifySchedule(scheduleId, date, modifyType, modifyScheduleRequestDto) ;

        ResponseDto<ModifyScheduleResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.SCHEDULE_MODIFY_SUCCESS, modifyScheduleResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/schedule/{scheduleId}/date/{date}/deleteType/{deleteType}")
    public ResponseEntity<ResponseDto<DeleteScheduleResponseDto>> deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @PathVariable("deleteType") String deleteType) {

        DeleteScheduleResponseDto deleteScheduleResponseDto = scheduleService.deleteSchedule(scheduleId, date, deleteType);

        ResponseDto<DeleteScheduleResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.SCHEDULE_DELETE_SUCCESS, deleteScheduleResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    
}
