package com.bside.sidefriends.quick.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class QuickHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quickHistoryId;

    // Quick 과 양방향 관계성
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quick_id")
    private Quick quick;

    // 퀵기록 날짜
    @Column(nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    // 퀵기록 실행 횟수
    private int count = 1;

    @Builder
    public QuickHistory(Long quickHistoryId, Quick quick, LocalDate createdAt, int count) {
        this.quickHistoryId = quickHistoryId;
        this.quick = quick;
        this.createdAt = createdAt;
        this.count = count;
    }

    public void increaseCount() {
        this.count += 1;
    }
}
