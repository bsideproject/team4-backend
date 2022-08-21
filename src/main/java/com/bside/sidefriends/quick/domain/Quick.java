package com.bside.sidefriends.quick.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Quick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quickId;

    // TODO-jh : PET 객체 관계 설정
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_id")
//    private Pet pet;
    private String petId;

    // Quick History 와 양방향 관계성
    @OneToMany(mappedBy = "quick", fetch = FetchType.LAZY)
    private List<QuickHistory> quickHistories = new ArrayList<>();

    // 퀵기록 명
    @Column(nullable = false)
    private String name;

    // 퀵기록 1일 체크 횟수
    @Column(nullable = false)
    private int total;

    // 퀵기록 상세 설명
    private String explanation;

    // 퀵기록 정렬 순서
    @Column(nullable = false, name = "quickOrder")
    private int order;

    // 퀵기록 유효 시작 시간
    @Column(updatable = false)
    private LocalDateTime startedAt;

    // 퀵기록 유효 종료 시간
    private LocalDateTime endedAt;

    public void changeEndedAt(LocalDateTime localDateTime) {
        this.endedAt = localDateTime;
    }

    @Builder
    public Quick(Long quickId, String petId, String name, int total, String explanation, int order, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.quickId = quickId;
        this.petId = petId;
        this.name = name;
        this.total = total;
        this.explanation = explanation;
        this.order = order;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }
}
