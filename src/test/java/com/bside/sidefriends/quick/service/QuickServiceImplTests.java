package com.bside.sidefriends.quick.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//TODO-jh
class QuickServiceImplTests {

    @Test
    @DisplayName("펫 최초 생성 시 퀵기록 Default 정보 4가지가 생성되야 한다")
    void 퀵기록_디폴트_생성() {
        // given : 테스트용 유저 생성
        // when : 유저가 펫을 신규 등록하는 경우
        // then : 펫의 디폴터 퀵기록 4가지 생성
    }


    @Test
    @DisplayName("퀵기록 목록 조회")
    void 퀵기록_목록_조회() {
        // given : Default 퀵정보 생성
        // when : 어떤 날짜를 조회하던
        // then : Default 퀵정보 목록을 리턴해야 한다


        // given : Default 퀵정보 생성 & 특정 날짜에 Update 이력 여러건
        // when : 해당 특정 날짜 데이터를 조회하면
        // then : Update 데이터 중 가장 마지막 데이터를 불러와야 한다

    }

    @Test
    @DisplayName("퀵기록 수정")
    void 퀵기록_수정() {
        // given : Default 퀵정보가 주어지고,
        // when : 퀵기록 수정을 하면,
        // then : 변경일 이전엔 이전 정보가 조회되고, 변경일 후에는 변경된 정보가 조회되어야 한다
        // then : 수정된 name, total, explanation 만 변경되고, order, pet 정보는 그대로 유지되야 한다
    }


    @Test
    @DisplayName("퀵기록 순서 변경")
    void 퀵기록_순서_변경() {
        // given : Default 퀵정보가 주어지고,
        // when : 1-2-3-4 ==> 2-1-3-4 순서를 변경하면
        // then : 변경일 이전 조회 시엔 1-2-3-4, 변경일 이후 조회 시엔 2-1-3-4가 조회되어야 한다
    }


    @Test
    @DisplayName("퀵기록 실행횟수 증가")
    void 퀵기록_실행횟수_증가() {
        // given : 최초 퀵기록 실행인 경우
        // when : 퀵 기록 실행횟수 증가 요청
        // then : 최초 요청인 경우 퀵기록 실행횟수 정보를 신규 생성하고 +1을 한다

        // given : 이미 퀵기록을 수행한 이력이 있는 경우
        // when : 퀵 기록 실행횟수 증가 요청
        // then : 퀵 기록 실행횟수 증가 성공
    }

    @Test
    @DisplayName("퀵기록 실행횟수 ")
    void 퀵기록_실행횟수_증가_초과시() {
        // given : (이미 실행횟수 달성 완료한) 테스트용 퀵 기록 생성
        // when : 퀵 기록 실행횟수 증가 요청
        // then : 퀵 기록 실행횟수 증가 실패
    }
}