package com.bside.sidefriends.checklist.domain;

import com.bside.sidefriends.checklist.service.dto.ModifyChecklistRequestDto;
import com.bside.sidefriends.pet.domain.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    // 기존값 = 자기 자신의 checklistId : 반복일정에서 이 할일만 수정한 경우, 부모 checklistId를 부여한다
    private Long originChecklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
//    private String petId;

    // Checklist Meta 데이터와의 양방향 관계성
    @OneToOne(mappedBy = "checklist", fetch = FetchType.LAZY)
    private ChecklistMeta checklistMeta;

    // Checklist History 데이터와의 양방향 관계성
//    @OneToOne(mappedBy = "checklist", fetch = FetchType.LAZY)
//    private ChecklistHistory checklistHistory;

    // 할일 제목
    private String title;

    // 할일 설명
    private String explanation;

    // 할일 수행일시
    @Column(nullable = false)
    private LocalDate date;

    // 할일 수행여부 : isRepeated 가 False 일 때만 사용하는 값 / True 이면 Checklist History 의 값을 사용해야 한다
    private boolean isDone = false;

    // 할일 반복여부
    private boolean isRepeated = false;

    public boolean getIsDone() {
        return isDone;
    }
    public boolean getIsRepeated() {
        return isRepeated;
    }

    @Builder
    public Checklist(Long originChecklistId, Pet pet, ChecklistMeta checklistMeta, ChecklistHistory checklistHistory, String title, String explanation, LocalDate date, boolean isDone, boolean isRepeated) {
        this.originChecklistId = originChecklistId;
        this.pet = pet;
        this.checklistMeta = checklistMeta;
//        this.checklistHistory = checklistHistory;
        this.title = title;
        this.explanation = explanation;
        this.date = date;
        this.isDone = isDone;
        this.isRepeated = isRepeated;
    }

    public void modify(ModifyChecklistRequestDto modifyChecklistRequestDto) {
        title = modifyChecklistRequestDto.getTitle();
        explanation = modifyChecklistRequestDto.getExplanation();
        date = modifyChecklistRequestDto.getDate();
        isDone = modifyChecklistRequestDto.getIsDone();
        isRepeated = modifyChecklistRequestDto.getIsRepeated();
    }

    public boolean changeIsDone() {
        this.isDone = !this.isDone;
        return this.isDone;
    }
}
