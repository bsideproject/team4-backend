package com.bside.sidefriends.schedule.domain;

import com.bside.sidefriends.schedule.service.dto.ModifyScheduleRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    // 기존값 = 자기 자신의 scheduleId
    // 반복일정에서 이 일정만 수정한 경우, 부모 scheduleId를 부여한다
    private Long originScheduleId;

    // TODO-jh : PET 객체 관계 설정
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_id")
//    private Pet pet;
    private String petId;

    // Schedule Meta 데이터와의 양방향 관계성
    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY)
    private ScheduleMeta scheduleMeta;

    // 일정 제목
    private String title;

    // 일정 설명
    private String explanation;

    // 일정 시작일
    @Column(nullable = false)
    private LocalDate startDate;

    // 일정 시작 시간 : null 인 경우, 시간 설정은 없단 의미
    private LocalTime startTime;

    // 일정 종료일시
    @Column(nullable = false)
    private LocalDate endDate;

    // 일정 종료 시간 : null 인 경우, 시간 설정은 없단 의미
    private LocalTime endTime;

    // 종일여부
    private boolean isAllDay;

    // 반복여부
    private boolean isRepeated;

    public boolean getIsAllDay() {
        return isAllDay;
    }
    public boolean getIsRepeated() {
        return isRepeated;
    }

    @Builder
    public Schedule(Long originScheduleId, String petId, ScheduleMeta scheduleMeta, String title, String explanation, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, boolean isAllDay, boolean isRepeated) {
        this.originScheduleId = originScheduleId;
        this.petId = petId;
        this.scheduleMeta = scheduleMeta;
        this.title = title;
        this.explanation = explanation;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.isAllDay = isAllDay;
        this.isRepeated = isRepeated;
    }

    public void setOriginScheduleId(Long id) {
        this.originScheduleId = id;
    }

    public void changeEndDate(LocalDate date) {
        endDate = date;
    }

    public void modify(ModifyScheduleRequestDto modifyScheduleRequestDto) {
        this.title = modifyScheduleRequestDto.getTitle();
        this.explanation = modifyScheduleRequestDto.getExplanation();
        this.startDate = modifyScheduleRequestDto.getStartDate();
        this.startTime = modifyScheduleRequestDto.getStartTime();
        this.endDate = modifyScheduleRequestDto.getEndDate() == null ? LocalDate.of(9999,12,31) : modifyScheduleRequestDto.getEndDate();
        this.endTime = modifyScheduleRequestDto.getEndTime();
        this.isAllDay = modifyScheduleRequestDto.getIsAllDay();
        this.isRepeated = modifyScheduleRequestDto.getIsRepeated();
    }

}
