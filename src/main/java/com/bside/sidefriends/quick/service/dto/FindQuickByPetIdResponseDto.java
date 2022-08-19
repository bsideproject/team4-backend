package com.bside.sidefriends.quick.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindQuickByPetIdResponseDto {

    private LocalDate date;

    @Getter
    private List<QuickDetail> quickDetailList;

    @Getter
    @AllArgsConstructor
    public static class QuickDetail {
        private Long quickId;
        private String name;
        private int count;
        private int total;
        private String explanation;
        private int order;
    }

}
