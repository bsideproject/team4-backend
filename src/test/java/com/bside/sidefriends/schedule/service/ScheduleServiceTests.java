package com.bside.sidefriends.schedule.service;

import com.bside.sidefriends.schedule.domain.Schedule;
import com.bside.sidefriends.schedule.repository.ScheduleMetaRepository;
import com.bside.sidefriends.schedule.repository.ScheduleRepository;
import com.bside.sidefriends.schedule.service.dto.CreateScheduleRequestDto;
import com.bside.sidefriends.schedule.service.dto.CreateScheduleResponseDto;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class ScheduleServiceTests {

    @BeforeEach
    public void beforeEach() {
    }

    ////// createSchedule //////
    @Test
    @DisplayName("반복 X인 일정 생성")
    void 일정_생성_반복X() {
        // given : ()
//        User user = userRepository.findByUserId();
//        CreateScheduleRequestDto createScheduleRequestDto = new CreateScheduleRequestDto(
//                "title", "explanation",
//                LocalDate.of(2022,1,1), LocalTime.of(00,00,00),
//                LocalDate.of(2022,12,31), LocalTime.of(23,59,59),
//                true, false, null
//        );
//        // when : 반복 없는 일정 생성
//        scheduleService.createSchedule(user, createScheduleRequestDto);
        // then : 일정 정상 생성
    }
    @Test
    @DisplayName("반복 O인 일정 생성")
    void 일정_생성_반복O() {
        // given : ()
        // when : 반복 있는 일정 생성
        // then : 일정, 반복일정 정보 생성
    }

    ////// findScheduleByScheduleId //////
    @Test
    @DisplayName("반복 X인 일정 조회")
    void 일정_조회_반복X() {
        // given : (반복 O 일정 생성)
        // when : 반복 없는 일정 조회
        // then : 일정 정상 조회
    }
    @Test
    @DisplayName("반복 O인 일정 조회")
    void 일정_조회_반복O() {
        // given : (반복 X 일정 생성)
        // when : 반복 있는 일정 조회
        // then : 일정, 반복일정 정상 조회
    }

    ////// findSchedule //////
    @Test
    @DisplayName("일정 전체 조회")
    void 일정_전체_조회() {
        // given : (특정 날짜에 해당되는 일정 여러건 생성)
        // when : 해당 날짜 전체 일정 조회
        // then : 해당 날짜 전체 일정 정상 조회
    }
    @Test
    @DisplayName("일정 전체 조회 + 매일 반복되는 일정 조회")
    void 일정_전체_조회_반복_매일() {
        // given : (1/1부터 매일 반복되는 일정 생성)
        // when : 이후 어떤 날짜를 조회하더라도
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매주 반복되는 일정 조회")
    void 일정_전체_조회_반복_매주() {
        // given : (1/1부터 매주 특정 요일마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매달 특정날짜에 반복되는 일정 조회")
    void 일정_전체_조회_반복_매달_특정날짜() {
        // given : (1/1부터 매달 특정 날짜마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매달 특정 주차의 요일에 반복되는 일정 조회")
    void 일정_전체_조회_반복_매달_특정주차의요일() {
        // given : (1/1부터 매달 특정 주차의 특정 요일마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }



    ////// modifySchedule //////
    @Test
    @DisplayName("일정 수정")
    void 일정_수정_반복X() {
        // given : (반복 없는 일정 생성)
        // when : 해당 일정 수정
        // then : 정상 수정
    }
    @Test
    @DisplayName("일정 수정 + 이 일정만 수정")
    void 일정_수정_반복O_이일정만수정() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 특정 날짜만 수정
        // then : 전체 반복 일정 조회 + 특정 날짜만 수정된 내용으로 조회
    }

    @Test
    @DisplayName("")
    void 일정_수정_반복O_이후일정모두수정() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 특정 날짜 이후 일정 모두 수정
        // then : 수정 이전 날짜는 수정 전 데이터 조회 + 수정 이후 날짜는 수정 후 데이터 조회
    }
    @Test
    @DisplayName("")
    void 일정_수정_반복O_전체일정모두수정() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 전체 일정 모두 수정
        // then : 전체 반복 일정 수정
    }

    ////// deleteSchedule //////
    @Test
    @DisplayName("")
    void 일정_삭제_반복X() {
        // given : (반복 없는 일정 생성)
        // when : 해당 일정 삭제
        // then : 정상 삭제
    }
    @Test
    @DisplayName("")
    void 일정_삭제_반복O_이일정만삭제() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 특정 날짜만 삭제
        // then : 전체 반복 일정 조회 + 특정 날짜는 조회되지 않음
    }

    @Test
    @DisplayName("")
    void 일정_삭제_반복O_이후일정모두삭제() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 특정 날짜 이후 모두 삭제
        // then : 특정 날짜 이전 데이터는 조회 가능 + 이후 데이터만 삭제
    }
    @Test
    @DisplayName("")
    void 일정_삭제_반복O_전체일정모두삭제() {
        // given : (반복하는 일정 생성)
        // when : 반복 일정의 전체 일정 모두 삭제
        // then : 전체 반복 일정 삭제
    }

    ////// modifySchedule + deleteSchedule + 반복 //////
    @Test
    @DisplayName("이 일정만 수정 + 전체 삭제")
    void 일정_수정_삭제_이일정만수정_전체삭제() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 데이터만 수정(3/1->3/2)
        // then : 전체 삭제 시, 특정일만 수정했던 것까지 함께 삭제되어야 한다 (when 적용 O)
    }
    @Test
    @DisplayName("이 일정만 수정 + 수정일 이전부터 삭제")
    void 일정_수정_삭제_이일정만수정_중간부터삭제() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 데이터만 수정(3/1->3/2)
        // then : 2/1에 이후 일정 모두 삭제 시, when 에서 변경했던 데이터도 함께 삭제되어야 하고 (when 적용 O)
        // 1월에는 매주 토요일 반복 데이터가 남아 있어야 한다
    }
    @Test
    @DisplayName("이 일정만 수정 + 전체 수정")
    void 일정_수정_삭제_이일정만수정_전체수정() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 데이터만 수정(3/1->3/2)
        // then : 전체 반복 일정 수정 시, when 을 제외한 전체 데이터가 수정되어야 하고
        // when 에서 수정한 데이터는 수정되지 않아야 한다 (when 적용 X)
    }
    @Test
    @DisplayName("이 일정만 수정 + 수정일 이전부터 수정")
    void 일정_수정_삭제_이일정만수정_중간부터수정() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 데이터만 수정(3/1->3/2)
        // then : 2/1에 이후 일정 모두 수정 시, when 에서 변경했던 데이터도 함께 수정되어야 하고 (when 적용 O)
        // 1월에는 수정 전 내용으로 조회되어야 한다
    }

    @Test
    @DisplayName("이후 일정 모두 수정 + 이후 일정 전체 삭제")
    void 일정_수정_삭제_이후일정모두수정_이후일정전체삭제() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 이후 일정 모두 수정
        // then : 3/1에서 전체 삭제 시, 3월 이후는 전체 삭제되고
        // 1,2월 데이터는 그대로 조회되어야 한다
    }
    @Test
    @DisplayName("이후 일정 모두 수정 + 이후 일정 전체 삭제")
    void 일정_수정_삭제_이후일정모두수정_이전일정전체삭제() {
        // given : 1/1부터 매주 토요일 반복
        // when : 3/1 이후 일정 모두 수정
        // then : 1/1에서 전체 삭제 시, 1,2월 데이터는 전체 삭제되고
        // 3월 이후 데이터는 그대로 조회되어야 한다
    }

}
