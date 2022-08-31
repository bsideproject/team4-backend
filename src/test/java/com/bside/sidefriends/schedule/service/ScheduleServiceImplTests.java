package com.bside.sidefriends.schedule.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//TODO-jh
class ScheduleServiceImplTests {




    ////// createSchedule //////
    @Test
    @DisplayName("반복 X인 일정 생성")
    void 일정_생성_반복X() { // PASS
        // given : ()
        // when : 반복 없는 일정 생성
        // then : 일정 정상 생성
    }
    @Test
    @DisplayName("반복 O인 일정 생성")
    void 일정_생성_반복O() { // PASS
        // given : ()
        // when : 반복 있는 일정 생성
        // then : 일정, 반복일정 정보 생성
    }

    ////// findScheduleByScheduleId //////
    @Test
    @DisplayName("반복 X인 일정 조회")
    void 일정_조회_반복X() { // PASS
        // given : (반복 O 일정 생성)
        // when : 반복 없는 일정 조회
        // then : 일정 정상 조회
    }
    @Test
    @DisplayName("반복 O인 일정 조회")
    void 일정_조회_반복O() { // PASS
        // given : (반복 X 일정 생성)
        // when : 반복 있는 일정 조회
        // then : 일정, 반복일정 정상 조회
    }

    ////// findSchedule //////
    @Test
    @DisplayName("일정 전체 조회")
    void 일정_전체_조회() { // PASS
        // given : (특정 날짜에 해당되는 일정 여러건 생성)
        // when : 해당 날짜 전체 일정 조회
        // then : 해당 날짜 전체 일정 정상 조회
    }
    @Test
    @DisplayName("일정 전체 조회")
    void 일정_전체_조회_기간내() { // PASS
        // given : (특정 날짜에 해당되는 일정 여러건 생성)
        // when : 해당 날짜 전체 일정 조회 / 날짜 범위 외 조회
        // then : 해당 날짜 전체 일정 정상 조회 / 날짜 범위 외 조회되지 않아야 함
    }
    @Test
    @DisplayName("일정 전체 조회 + 매일 반복되는 일정 조회")
    void 일정_전체_조회_반복_매일() { // PASS
        // given : (1/1부터 매일 반복되는 일정 생성)
        // when : 이후 어떤 날짜를 조회하더라도
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매주 반복되는 일정 조회")
    void 일정_전체_조회_반복_매주() { // PASS
        // given : (1/1부터 매주 특정 요일마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매달 특정날짜에 반복되는 일정 조회")
    void 일정_전체_조회_반복_매달_특정날짜() { // PASS
        // given : (1/1부터 매달 특정 날짜마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }
    @Test
    @DisplayName("일정 전체 조회 + 매달 특정 주차의 요일에 반복되는 일정 조회")
    void 일정_전체_조회_반복_매달_특정주차의요일() { // PASS
        // given : (1/1부터 매달 특정 주차의 특정 요일마다 반복되는 일정 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 일정이 조회되어야 한다
    }

    ////// modifySchedule //////
    @Test
    @DisplayName("일정 수정")
    void 일정_수정_반복X() { // PASS
        // given : (반복 없는 일정 생성)
        // when : 해당 일정 수정
        // then : 정상 수정
    }
    @Test
    @DisplayName("일정 수정 + 이 일정만 수정")
    void 일정_수정_반복O_이일정만수정() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 특정 날짜만 수정 (2022-07-01)
        // then : 특정 날짜만 수정된 내용으로 조회 (2022-07-01)
        // then : 나머지 날짜를 조회하면 수정 전 내용으로 조회 (ex. 2022-07-02)
    }

    @Test
    @DisplayName("")
    void 일정_수정_반복O_이후일정모두수정() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 특정 날짜 이후 일정 모두 수정 (2022-07-01)
        // then : 수정 이전 날짜는 수정 전 데이터 조회 (2022-07-01 이전) (ex. 2022-06-30)
        // then : 수정 이후 날짜는 수정 후 데이터 조회 (2022-07-01 이후)
    }
    @Test
    @DisplayName("")
    void 일정_수정_반복O_전체일정모두수정_반복여부NN() { // PASS
        // '일정_수정_반복X'
    }
    @Test
    @DisplayName("이전엔 연관된 meta 는 없고, 이후에는 연관된 meta 가 생성돼야 한다")
    void 일정_수정_반복O_전체일정모두수정_반복여부NY() { // PASS
        // given : (반복 없는 일정 생성)
        // when : 일정의 전체 일정 모두 수정 && 반복 신규 생성 (2022-07-01 ~ 2022-08-01, daily)
        // then : 전체 반복 일정이 수정되고, 신규 ScheduleMeta 데이터도 생성되야 한다
        // then : 반복 일정 내 데이터는 조회 가능 (2022-07-01 ~ 2022-08-01)
        // then : 반복 일정 외 데이터는 조회 불가 (ex. 2022-06-30, 2022-08-02)
    }
    @Test
    @DisplayName("이전에는 연관된 meta 는 있고, 이후에도 연관된 meta 가 없어야 한다 (삭제)")
    void 일정_수정_반복O_전체일정모두수정_반복여부YN() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 전체 일정 모두 수정 && 반복 일정 없도록 수정
        // then : 전체 반복 일정이 수정되고,
        // then : 기존 ScheduleMeta 데이터는 삭제되야 한다
    }
    @Test
    @DisplayName("이전엔 연관된 meta 가 있고, 이후엔 연관된 meta 의 내용만 수정되면 된다")
    void 일정_수정_반복O_전체일정모두수정_반복여부YY() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 전체 일정 모두 수정 && 반복 일정도 수정
        // then : 전체 반복 일정이 수정되고,
        // then : 기존 ScheduleMeta 데이터도 수정되야 한다
    }

    ////// deleteSchedule //////
    @Test
    @DisplayName("")
    void 일정_삭제_반복X() { // PASS
        // given : (반복 없는 일정 생성)
        // when : 해당 일정 삭제
        // then : 정상 삭제
    }
    @Test
    @DisplayName("")
    void 일정_삭제_반복O_이일정만삭제() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 특정 날짜만 삭제 (2022-07-01)
        // then : 전체 반복 일정 조회 가능 (ex. 2022-05-01)
        // then : 특정 날짜만 조회되지 않음 (2022-07-01)

        // when : 반복 일정의 특정 날짜만 삭제 (2022-07-02)
        // then : 전체 반복 일정 조회 가능 (ex. 2022-05-01)
        // then : 특정 날짜만 조회되지 않음 (2022-07-02)
    }

    @Test
    @DisplayName("")
    void 일정_삭제_반복O_이후일정모두삭제() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 특정 날짜 이후 모두 삭제 (2022-07-01)
        // then : 특정 날짜 이전 데이터는 조회 가능 (2022-07-01 이전) (ex. 2022-06-30)
        // then : 특정 날짜 이후 데이터는 조회 불가 (2022-07-01 이후)
    }
    @Test
    @DisplayName("")
    void 일정_삭제_반복O_전체일정모두삭제() { // PASS
        // given : 매일 반복하는 일정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 일정의 전체 일정 모두 삭제
        // then : 전체 반복 일정 삭제
    }

    ////// modifySchedule + deleteSchedule + 반복 //////
    @Test
    @DisplayName("이 일정만 수정 + 전체 삭제")
    void 일정_수정_삭제_이일정만수정_전체삭제() { // PASS
        // given : 7/1부터 매일 반복
        // when : 7/2 데이터만 수정
        // then : 전체 삭제 시, 특정일만 수정했던 것까지 함께 삭제되어야 한다 (when 적용 O)
    }
    @Test
    @DisplayName("이 일정만 수정 + 수정일 이전부터 삭제")
    void 일정_수정_삭제_이일정만수정_중간부터삭제() { // PASS
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정
        // then : 8/1에 이후 일정 모두 삭제 시, when 에서 변경했던 데이터도 함께 삭제되어야 하고 (when 적용 O)
        // then : 7월에는 매주 토요일 반복 데이터가 남아 있어야 한다
    }
    @Test
    @DisplayName("이 일정만 수정 + 전체 수정")
    void 일정_수정_삭제_이일정만수정_전체수정_반복YY() { // PASS
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 전체 반복 일정 수정 (7/1부터 매주 토요일 반복)
        // then : when 을 제외한 전체 데이터가 수정되어야 하고, when 에서 수정한 데이터는 수정되지 않아야 한다 (when 적용 X)
        // then : 7/1부터 매주 토요일에 해당 일정은 조회되어야 한다
    }
    @Test
    @DisplayName("이 일정만 수정 + 전체 수정")
    void 일정_수정_삭제_이일정만수정_전체수정_반복YN() { // PASS
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 전체 반복 일정 수정
        // then : when 을 제외한 전체 데이터가 수정되어야 하고, when 에서 수정한 데이터는 수정되지 않아야 한다 (when 적용 X)
    }
    @Test
    @DisplayName("이 일정만 수정 + 수정일 이전부터 수정")
    void 일정_수정_삭제_이일정만수정_중간부터수정() { // PASS, but Diff
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 8/1 이후 일정 모두 수정
        // then : 8/1 이후 데이터는 모두 수정되고
        // then : 9/1 은 이 일정만 수정한 값도 조회되고, 전체 수정한 값도 조회된다 (when 수정 적용 X)


        // 네이버 캘린더에서의 동작
        // then : 8/1에 이후 일정 모두 수정 시, when 에서 변경했던 데이터도 함께 수정되어야 하고 (when 적용 O)
        // then : 7월에는 수정 전 내용으로 조회되어야 한다
        // then : 9/1에는 기존에 이 일정만 수정한 값이 삭제되고, 전체 수정한 값만 조회된다 **이 부분만 다르다**
    }

    @Test
    @DisplayName("이후 일정 모두 수정 + 이후 일정 전체 삭제")
    void 일정_수정_삭제_이후일정모두수정_이후일정전체삭제() { // PASS
        // given : 7/1부터 매일 반복
        // when : 9/1 이후 일정 모두 수정 && 8/1 이후 일정 전체 삭제
        // then : 8월 이후는 전체 삭제되고 (ex. 2022-08-01, 2022-09-01)
        // then : 7월 데이터는 정상 조회되야 한다 (ex. 2022-07-01, 2022-07-31)
    }
    @Test
    @DisplayName("이후 일정 모두 수정 + 이후 일정 전체 삭제")
    void 일정_수정_삭제_이후일정모두수정_이전일정전체삭제() { // PASS, but Diff
        // given : 7/1부터 매일 반복
        // when : 9/1 이후 일정 모두 수정 && 7/1 이후 일정 전체 삭제
        // then : 7월, 8월 모든 데이터가 삭제돼버린다

        // 네이버 캘린더에서의 동작
        // then : 8월 이후는 정상 조회되고 (ex. 2022-08-01, 2022-09-01)
        // then : 7월 데이터는 삭제되야 한다 (ex. 2022-07-01, 2022-07-31)
    }

}