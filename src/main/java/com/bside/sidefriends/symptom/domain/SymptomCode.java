package com.bside.sidefriends.symptom.domain;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

@Getter
public enum SymptomCode {

    DIARRHEA("설사를 해요."),
    CONSTIPATION("변비가 있어요."),
    HARD_STOOL("대변이 평소보다 딱딱해요."),
    BLOODY_STOOL("혈변 증세가 있어요."),
    BLOODY_URINE("혈뇨 증세가 있어요."),
    YELLOW_URINE("소변이 평소보다 노래요."),
    FOAM_IN_URINE("소변에 거품이 섞여있어요."),
    URINE_OFTEN("소변을 평소보다 자주 봐요."),
    URINE_LESS("소변을 평소보다 적게 봐요."),
    TOILET_MISTAKES("배변실수가 잦아요."),
    HARD_BREATHING("숨쉬는 걸 어렵거나 괴로워해요."),
    CONVULSIONS("경련을 일으켜요."),
    GOOSE_SOUND("거위소리를 내요."),
    SNEEZE("재채기를 자주 해요."),
    COUGH("밤이나 새벽에 기침을 심하게 해요."),
    SNORE("코를 심하게 골아요."),
    BLOODY_VOMIT("피가 섞인 토를 했어요."),
    WHITE_VOMIT("흰색 토를 했어요."),
    GREEN_VOMIT("초록색 토를 했어요."),
    YELLOW_SKIN("피부나 점막이 노래요."),
    RASH("발진이 있어요."),
    NO_ELASTICITY("탄력이 없고 당겼을 때 금방 돌아오지 않아요."),
    FEVER("열이 나요."),
    HAIR_LOSS("털이 많이 빠져요."),
    ITCHY_SKIN("자주 가려움증이 있어 몸을 긁어요."),
    OOZE("진물이 나요."),
    BUMP("혹이 만져져요."),
    TEAR_OFTEN("눈물이나 눈꼽이 평소보다 많아요."),
    SWOLLEN_EYES("눈이 부어있어요."),
    ITCH_EYES("눈을 자꾸 비벼요."),
    BLINK_OFTEN("눈을 자주 깜빡여요."),
    WHITE_EYES("눈이 희끗해졌어요."),
    BLOODY_NOSE("코피가 나요."),
    RUNNY_NOSE("콧물을 계속 흘려요."),
    DRY_NOSE("코가 말라있어요."),
    PURPLE_MOUTH("입술이나 혀가 보라색이에요."),
    WHITE_MOUTH("입술이나 혀가 하얀색이에요."),
    HARD_CHEWING("사료/간식을 잘 씹지 못해요."),
    DROOL_OFTEN("침을 많이 흘려요."),
    SWOLLEN_MOUTH("입술/잇몸이 부었어요."),
    WHITE_GUM("잇몸이 하얘요."),
    STINKY_MOUTH("구취가 심해졌어요."),
    STINKY_EAR("귀에서 평소보다 냄새가 많이 나요."),
    MORE_EARWAX("평소보다 귀지가 너무 많이 나와요."),
    BROWN_EARWAX("귀지 색이 진한 갈색이에요."),
    SWOLLEN_BELLY("배가 부어올라있어요."),
    WIERD_WALKING("걷는 모습이 평소와 달라요."),
    INSTEP_DRAGGING("발등을 바닥에 끌면서 걸어요."),
    LOSING_FOCUS("중심을 잘 못 잡고 걸어요."),
    GENITAL_PAIN("대소변시 생식기 통증을 느껴요."),
    URINE_MORE("소변을 너무 자주/많이 봐요."), // TODO: 소변을 평소보다 자주 봐요와 무엇이 다른지 논의 필요. IR.
    TILTING_HEAD("고개를 기울인 채로 있어요."),
    PARALYZED_FACE("얼굴이 마비되어 있어요."),
    APPETITE("식욕이 변했어요."),
    VITALITY("활력이 떨어져요."),
    SENSITIVITY("평소보다 과민하게 반응해요."),
    TREMBLING("몸을 떨어요."),
    SEIZURE("발작/경련을 일으켜요."),
    SPINNING("원을 그리며 빙빙 돌아요."),
    PRAYING("기도하는 자세를 취해요."),
    WEIGHT_CHANGE("갑자기 살이 많이 쪘어요/빠졌어요."),
    ET_CETERA("그 외 다른 이상징후가 있어요.");

    private String description;

    SymptomCode(String description) {
        this.description = description;
    }

    public static String of(String description) {

        SymptomCode descriptionCode = DESCRIPTIONS.get(description);

        if (descriptionCode == null) {
            return null;
        }

        return descriptionCode.toString();
    }

    private static Map<String, SymptomCode> DESCRIPTIONS = new HashMap<>();

    static {
        for (SymptomCode sc : values()) {
            DESCRIPTIONS.put(sc.getDescription(), sc);
        }

        DESCRIPTIONS = Collections.unmodifiableMap(DESCRIPTIONS);

    }

}

