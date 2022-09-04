package com.bside.sidefriends.checklist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class ChecklistHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistHistoryId;

    // Checklist 와의 양방향 관계성 (1대1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklistId")
    private Checklist checklist;

    // 할일 수행날짜
    private LocalDate date;

    // 할일 수행여부
    private Boolean isDone = true;

    public boolean getIsDone() {
        return isDone;
    }

    public boolean changeIsDone() {
        this.isDone = !this.isDone;
        return this.isDone;
    }

    @Builder
    public ChecklistHistory(Checklist checklist, LocalDate date, Boolean isDone) {
        this.checklist = checklist;
        this.date = date;
        this.isDone = isDone;
    }
}
