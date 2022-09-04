package com.bside.sidefriends.checklist.domain;

import com.bside.sidefriends.checklist.service.dto.ModifyChecklistRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class ChecklistMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistMetaId;

    // Checklist 와의 양방향 관계성 (1대1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklistId")
    private Checklist checklist;

    // 반복 주기 (daily, weekly, monthly, yearly)
    private String eventPeriod;

    // 반복 요일 (ex. 월,수,금)
    private String eventDate;

    // 반복 월 (ex. 3,6,9,12)
    private String eventMonth;

    // 반복 날짜 (ex. 15,30)
    private String eventDay;

    // 반복 주차 (ex. 1,3)
    private String eventWeek;

    // 반복 시작일시
    private LocalDate startedAt;

    // 반복 종료일시
    private LocalDate endedAt;

    // 반복 일정 제외 날짜 (ex. 2022-07-07, 2022-08-15)
    private String eventExceptionDate;

    // 반복 일정 삭제 날짜
    private String eventDeleteDate;

    @Builder
    public ChecklistMeta(Checklist checklist, String eventPeriod, String eventDate, String eventMonth, String eventDay, String eventWeek, LocalDate startedAt, LocalDate endedAt, String eventExceptionDate, String eventDeleteDate) {
        this.checklist = checklist;
        this.eventPeriod = eventPeriod;
        this.eventDate = eventDate;
        this.eventMonth = eventMonth;
        this.eventDay = eventDay;
        this.eventWeek = eventWeek;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.eventExceptionDate = eventExceptionDate;
        this.eventDeleteDate = eventDeleteDate;
    }

    public void changeEndedAt(LocalDate date) {
        endedAt = date;
    }

    public void addEventExceptionDate(LocalDate date) {
        String date_string = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (eventExceptionDate == null) {
            eventExceptionDate = date_string;
        } else {
            eventExceptionDate += "," + date_string;
        }
    }

    public void addEventDeleteDate(LocalDate date) {
        String date_string = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (eventDeleteDate == null) {
            eventDeleteDate = date_string;
        } else {
            eventDeleteDate += "," + date_string;
        }
    }

    public void modify(ModifyChecklistRequestDto.RepeatDetail repeatDetail) {
        this.eventPeriod = repeatDetail.getEventPeriod();
        this.eventDate = repeatDetail.getEventDate();
        this.eventMonth = repeatDetail.getEventMonth();
        this.eventDay = repeatDetail.getEventDay();
        this.eventWeek = repeatDetail.getEventWeek();
        this.startedAt = repeatDetail.getStartedAt();
        this.endedAt = repeatDetail.getEndedAt();
    }

    public void deleteDateOnExceptionDateList(LocalDate date) {
        List<String> exceptionDateList = Arrays.asList(eventExceptionDate.split(","));

        exceptionDateList = exceptionDateList.stream()
                .map(s -> {
                    if (s.equals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                        return null;
                    } else {
                        return s;
                    }
                }).filter(s -> s != null)
                .collect(Collectors.toList());

        this.eventExceptionDate = exceptionDateList.size() == 0 ? null : String.join(",", exceptionDateList);
    }

}

