package com.bside.sidefriends.checklist.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChecklistServiceImplTests {

    ////// createChecklist //////
    @Test
    @DisplayName("반복 X인 할일 생성")
    void 할일_생성_반복X() { // PASS
        // given : ()
        // when : 반복 X 할일 생성
        // then : 할일 정상 생성
    }
    @Test
    @DisplayName("반복 O인 할일 생성")
    void 할일_생성_반복O() { // PASS
        // given : ()
        // when : 반복 O 할일 생성
        // then : 할일, 반복할일 정보 생성
    }

    ////// findChecklistByChecklistId //////
    @Test
    @DisplayName("반복 X인 할일 조회")
    void 할일_조회_반복X() { // PASS
        // given : 반복 X 할일 생성
        // when : 반복 없는 할일 조회
        // then : 할일 정상 조회
    }
    @Test
    @DisplayName("반복 O인 할일 조회")
    void 할일_조회_반복O() { // PASS
        // given : 반복 O 할일 생성
        // when : 반복 있는 할일 조회
        // then : 할일, 반복할일 정상 조회
    }

    ////// findChecklist //////
    @Test
    @DisplayName("할일 전체 조회")
    void 할일_전체_조회() { // PASS
        // given : (특정 날짜에 해당되는 할일 여러건 생성)
        // when : 해당 날짜 전체 할일 조회
        // then : 해당 날짜 전체 할일 정상 조회
    }
    @Test
    @DisplayName("할일 전체 조회")
    void 할일_전체_조회_날짜범위외() { // PASS
        // given : (특정 날짜에 해당되는 할일 여러건 생성)
        // when : 해당 날짜 전체 할일 조회 / 날짜 범위 외 조회
        // then : 해당 날짜 전체 할일 정상 조회 / 날짜 범위 외 조회되지 않아야 함
    }
    @Test
    @DisplayName("할일 전체 조회 + 매일 반복되는 할일 조회")
    void 할일_전체_조회_반복_매일() { // PASS
        // given : (매일 반복되는 할일 생성)
        // when : 이후 어떤 날짜를 조회하더라도
        // then : 해당 반복 할일이 조회되어야 한다
    }
    @Test
    @DisplayName("할일 전체 조회 + 매주 반복되는 할일 조회")
    void 할일_전체_조회_반복_매주() { // PASS
        // given : (매주 특정 요일마다 반복되는 할일 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 할일이 조회되어야 한다
    }
    @Test
    @DisplayName("할일 전체 조회 + 매달 특정날짜에 반복되는 할일 조회")
    void 할일_전체_조회_반복_매달_특정날짜() { // PASS
        // given : (매달 특정 날짜마다 반복되는 할일 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 할일이 조회되어야 한다
    }
    @Test
    @DisplayName("할일 전체 조회 + 매달 특정 주차의 요일에 반복되는 할일 조회")
    void 할일_전체_조회_반복_매달_특정주차의요일() { // PASS
        // given : (매달 특정 주차의 특정 요일마다 반복되는 할일 생성)
        // when : 이후 반복 요일의 날짜를 조회하면
        // then : 해당 반복 할일이 조회되어야 한다
    }

    ////// modifyChecklist //////
    @Test
    @DisplayName("할일 수정")
    void 할일_수정_반복X() { // PASS
        // given : (반복 없는 할일 생성)
        // when : 해당 할일 수정
        // then : 정상 수정
    }
    @Test
    @DisplayName("할일 수정 + 이 할일만 수정")
    void 할일_수정_반복O_이할일만수정() { // PASS, diff
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 특정 날짜만 수정 (2022-07-01)
        // then : 특정 날짜만 수정된 내용으로 조회 (2022-07-01)
        // then : 나머지 날짜를 조회하면 수정 전 내용으로 조회 (ex. 2022-07-02)
        // then+ : 체크여부는 FE 에서 받은 값을 사용한다

        // 네이버 캘린더에서는
        // 체크여부=Y인 경우, 이 할일만 수정이 비활성화되버린다 = 별도의 컬럼으로 생성한듯?
    }
    @Test
    @DisplayName("")
    void 할일_수정_반복O_이후할일모두수정() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 특정 날짜 이후 할일 모두 수정 (2022-07-01)
        // then : 수정 이전 날짜는 수정 전 데이터 조회 (2022-07-01 이전) (ex. 2022-06-30)
        // then : 수정 이후 날짜는 수정 후 데이터 조회 (2022-07-01 이후)
        // then+ : 이후 할일 모두 수정 시, 이후 할일의 체크여부도 모두 날라간다
    }
    @Test
    @DisplayName("")
    void 할일_수정_반복O_전체할일모두수정_할일여부NN() { // PASS
        // '할일_수정_반복X'
    }
    @Test
    @DisplayName("이전엔 연관된 meta 는 없고, 이후에는 연관된 meta 가 생성돼야 한다")
    void 할일_수정_반복O_전체할일모두수정_반복여부NY() { // PASS
        // given : (반복 없는 할일 생성)
        // when : 할일의 전체 할일 모두 수정 && 반복 신규 생성 (2022-07-01 ~ 2022-08-01, daily)
        // then : 전체 반복 할일이 수정되고, 신규 ScheduleMeta 데이터도 생성되야 한다
        // then : 반복 할일 내 데이터는 조회 가능 (2022-07-01 ~ 2022-08-01)
        // then : 반복 할일 외 데이터는 조회 불가 (ex. 2022-06-30, 2022-08-02)
    }
    @Test
    @DisplayName("이전에는 연관된 meta 는 있고, 이후에도 연관된 meta 가 없어야 한다 (삭제)")
    void 할일_수정_반복O_전체할일모두수정_반복여부YN() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 전체 할일 모두 수정 && 반복 할일 없도록 수정
        // then : 전체 반복 할일이 수정되고,
        // then : 기존 ChecklistMeta 데이터는 삭제되야 한다
        // then+ : 기존의 ChecklistHistory 데이터도 모두 삭제되야 한다
    }
    @Test
    @DisplayName("이전엔 연관된 meta 가 있고, 이후엔 연관된 meta 의 내용만 수정되면 된다")
    void 할일_수정_반복O_전체할일모두수정_반복여부YY() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 전체 할일 모두 수정 && 반복 할일도 수정
        // then : 전체 반복 할일이 수정되고,
        // then : 기존 ChecklistMeta 데이터도 수정되야 한다
        // then+ : 기존의 ChecklistHistory 데이터도 모두 삭제되야 한다 (ex. 매주 목요일마다 반복 -> 매주 금요일마다 반복)
    }

    ////// deleteChecklist //////
    @Test
    @DisplayName("")
    void 할일_삭제_반복X() { // PASS
        // given : (반복 없는 할일 생성)
        // when : 해당 할일 삭제
        // then : 정상 삭제
    }
    @Test
    @DisplayName("")
    void 할일_삭제_반복O_이할일만삭제() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 특정 날짜만 삭제 (2022-07-01)
        // then : 전체 반복 할일 조회 가능 (ex. 2022-05-01)
        // then : 특정 날짜만 조회되지 않음 (2022-07-01)

        // given : 위에 이어서 진행 (복수 건 삭제 시)
        // when : 반복 할일의 특정 날짜만 삭제 (2022-07-02)
        // then : 전체 반복 할일 조회 가능 (ex. 2022-05-01)
        // then : 특정 날짜만 조회되지 않음 (2022-07-02)
    }
    @Test
    @DisplayName("")
    void 할일_삭제_반복O_이후할일모두삭제() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // given : 삭제일 전후로 checklistHistory 각각 생성
        // when : 반복 할일의 특정 날짜 이후 모두 삭제 (2022-07-01)
        // then : 특정 날짜 이전 데이터는 조회 O (2022-07-01 이전) (ex. 2022-06-30)
        // then : 특정 날짜 이후 데이터는 조회 X (2022-07-01 이후)
        // then+ : 특정 날짜 이전의 데이터 중 checkHistory 의 데이터도 함께 삭제 X
        // then+ : 특정 날짜 이후의 데이터 중 checkHistory 의 데이터도 함께 삭제 O
    }
    @Test
    @DisplayName("")
    void 할일_삭제_반복O_전체할일모두삭제() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-01-01 ~ 2022-12-31, daily)
        // given : checklistHistory 데이터 생성
        // when : 반복 할일의 전체 할일 모두 삭제
        // then : Checklist 삭제
        // then : ChecklistMeta 삭제
        // then+ : 전체 반복 할일의 checkHistory 도 모두 삭제
    }

    ////// modifyChecked //////
    @Test
    @DisplayName("반복 X인 할일의 체크여부 변경")
    void 할일_수행여부_변경_반복X() { // PASS
        // given : (반복 X인 할일 생성)
        // when : 할일의 수행여부 변경 (true <-> false)
        // then : isDone 값을 true -> false, false -> true 로 변경
    }
    @Test
    @DisplayName("반복 O인 할일의 체크여부 변경")
    void 할일_수행여부_변경_반복O() { // PASS
        // given : (매일 반복하는 할일 생성)
        // when : 할일의 수행여부 변경 (true <-> false)
        // then : checkHistory 에 없는 경우, checkHistory 에 isDone = true 로 신규 생성
        // then : checkHistory 에 있는 경우, isDone 값을 true -> false, false -> true 로 변경
    }
    @Test
    @DisplayName("반복에서 이 할일만 변경한 경우의 체크여부 변경")
    void 할일_수행여부_변경_이할일만변경() { // PASS
        // given : (매일 반복하는 할일 생성 && 특정일의 이 할일만 변경)
        // when : 할일의 수행여부 변경 (true <-> false)
        // then : isDone 컬럼을 true -> false, false -> true 로 변경
    }

    ////// 수정 + 수정 복합 테스트 //////
    @Test
    @DisplayName("이 할일만 수정 + 전체 수정")
    void 할일_수정삭제_이할일만수정_전체수정_반복YY() {
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 전체 반복 할일 수정 (7/1부터 매주 토요일 반복)
        // then : when 을 제외한 전체 데이터가 수정되어야 하고, when 에서 수정한 데이터는 수정되지 않아야 한다 (when 적용 X)
        // then : 7/1부터 매주 토요일에 해당 할일은 조회되어야 한다
        // then+ : 기존 반복할일의 ChecklistHistory 는 전부 삭제되고, 이 할일만 수정한 데이터의 ChecklistHistory 는 삭제되지 않는다
    }
    @Test
    @DisplayName("이 할일만 수정 + 전체 수정")
    void 할일_수정삭제_이할일만수정_전체수정_반복YN() {
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 전체 반복 할일 수정
        // then : when 을 제외한 전체 데이터가 수정되어야 하고, when 에서 수정한 데이터는 수정되지 않아야 한다 (when 적용 X)
        // then+ : 기존 반복할일의 ChecklistHistory 는 전부 삭제되고, 이 할일만 수정한 데이터의 ChecklistHistory 는 삭제되지 않는다
    }
    @Test
    @DisplayName("이 할일만 수정 + 수정일 이전부터 수정")
    void 할일_수정삭제_이할일만수정_중간부터수정() { // Diff
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정 && 8/1 이후 할일 모두 수정
        // then : 8/1 이후 데이터는 모두 수정되고
        // then : 9/1 은 이 할일만 수정한 값도 조회되고, 전체 수정한 값도 조회된다 (when 수정 적용 X)


        // 네이버 캘린더에서의 동작
        // then : 8/1에 이후 할일 모두 수정 시, when 에서 변경했던 데이터도 함께 수정되어야 하고 (when 적용 O)
        // then : 7월에는 수정 전 내용으로 조회되어야 한다
        // then : 9/1에는 기존에 이 할일만 수정한 값이 삭제되고, 전체 수정한 값만 조회된다 **이 부분만 다르다**
    }

    ////// 수정 + 삭제 복합 테스트 //////
    @Test
    @DisplayName("이 할일만 수정 + 전체 삭제")
    void 할일_수정삭제_이할일만수정_전체삭제() {
        // given : 7/1부터 매일 반복
        // when : 7/2 데이터만 수정 & 체크여부 변경 N -> Y
        // then : 전체 삭제 시, 특정일만 수정했던 것까지 함께 삭제되어야 한다 (when 적용 O)
        // then+ : 특정 일의 체크여부 데이터도 함께 삭제되야 한다
    }
    @Test
    @DisplayName("추가")
    void 할일_수정삭제_이할일만수정존재_전체할일모두삭제 () {
        // given : 매일 반복하는 할일 생성 && 이 할일만 수정 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 전체 할일 모두 삭제
        // then : 전체 반복 할일 삭제 && 이 할일만 수정도 삭제
    }
    @Test
    @DisplayName("이 할일만 수정 + 수정일 이전부터 삭제")
    void 할일_수정삭제_이할일만수정_중간부터삭제() {
        // given : 7/1부터 매일 반복
        // when : 9/1 데이터만 수정
        // then : 8/1에 이후 할일 모두 삭제 시, when 에서 변경했던 데이터도 함께 삭제되어야 하고 (when 적용 O)
        // then : 7월에는 매주 토요일 반복 데이터가 남아 있어야 한다
        // then+ : 8월 이후의 ChecklistHistory 데이터도 함께 삭제되야 한다
    }
    @Test
    @DisplayName("추가")
    void 할일_수정삭제_이할일만수정존재_이후할일모두삭제() {
        // given : 매일 반복하는 할일 생성 && 삭제일 전후로 이할일만수정 하나씩 생성 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 특정 날짜 이후 모두 삭제 (2022-07-01)
        // then : 특정 날짜 이전 데이터는 조회 O (2022-07-01 이전) (ex. 2022-06-30)
        // then : 특정 날짜 이후 데이터는 조회 X (2022-07-01 이후)
        // then : 삭제일 이전의 '이 할일만 수정' 데이터는 삭제 X
        // then : 삭제일 이후의 '이 할일만 수정' 데이터는 삭제 O
        // then+ : 특정 날짜 이전의 데이터 중 checkHistory 의 데이터도 함께 삭제 X
        // then+ : 특정 날짜 이후의 데이터 중 checkHistory 의 데이터도 함께 삭제 O
    }
    @Test
    @DisplayName("이후 할일 모두 수정 + 이후 할일 전체 삭제")
    void 할일_수정삭제_이후할일모두수정_이후할일전체삭제() {
        // given : 7/1부터 매일 반복
        // when : 9/1 이후 할일 모두 수정 && 8/1 이후 할일 전체 삭제
        // then : 8월 이후는 전체 삭제되고 (ex. 2022-08-01, 2022-09-01)
        // then : 7월 데이터는 정상 조회되야 한다 (ex. 2022-07-01, 2022-07-31)
        // then+ : 8월 이후 ChecklistHistory 데이터는 삭제 O
        // then+ : 7월의 ChecklistHistory 데이터는 삭제 X
    }
    @Test
    @DisplayName("이후 할일 모두 수정 + 이후 할일 전체 삭제")
    void 할일_수정삭제_이후할일모두수정_이전할일전체삭제() { // PASS, diff?
        // given : 매일 반복하는 할일 생성 (7/1부터 매일 반복)
        // given : 이후 할일 모두 수정 (9/1~)
        // when : 할일 전체 삭제 (7/1)
        // then : 9월 이후의 데이터는 삭제 X
        // then : 7,8월 모든 데이터는 삭제 O
        // then+ : 9월 이후의 ChecklistHistory 데이터는 삭제 X
        // then+ : 9월 이후의 ChecklistHistory 데이터는 삭제 O

        // 네이버 캘린더에서의 동작
        // then : 8월 이후는 정상 조회되고 (ex. 2022-08-01, 2022-09-01)
        // then : 7월 데이터는 삭제되야 한다 (ex. 2022-07-01, 2022-07-31)
    }

    ////// 수정 + 삭제 + 체크여부 복합 테스트 //////
    @Test
    @DisplayName("추가")
    void 할일_수정삭제_이후할일모두삭제_체크여부존재() { // PASS
        // given : 매일 반복하는 할일 생성 && 삭제일 전후로 체크여부 하나씩 변경 (2022-01-01 ~ 2022-12-31, daily)
        // when : 반복 할일의 특정 날짜 이후 모두 삭제 (2022-07-01)
        // then : 특정 날짜 이전 데이터는 조회 O (2022-07-01 이전) (ex. 2022-06-30)
        // then : 특정 날짜 이후 데이터는 조회 X (2022-07-01 이후)
        // then+ : 삭제일 이전의 체크여부 데이터는 삭제 X
        // then+ : 삭제일 이후의 체크여부 데이터도 삭제 O
    }
    @Test
    @DisplayName("추가")
    void 할일_수정삭제_이할일만수정존재_전체삭제() { // PASS
        // given : 매일 반복하는 할일 생성 (2022-07-01 ~ 2022-12-31, daily)
        // given : 삭제일 전후로 이할일만수정 하나씩 생성 (2022-07-25, 2022-08-25)
        // when : 전체 삭제
        // then : Checklist 삭제
        // then : ChecklistMeta 삭제
        // then : 삭제일 이전의 '이 할일만 수정' 데이터는 삭제 O
        // then : 삭제일 이후의 '이 할일만 수정' 데이터는 삭제 O
        // then+ : 특정 날짜 이전의 데이터 중 checkHistory 의 데이터도 함께 삭제 O
        // then+ : 특정 날짜 이후의 데이터 중 checkHistory 의 데이터도 함께 삭제 O
    }
}