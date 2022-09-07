package com.bside.sidefriends.quick.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ChangeQuickOrderRequestDto {

    @Getter
    private List<QuickOrder> quickOrderList;

    @Getter
    @AllArgsConstructor
    public static class QuickOrder {
        private Long quickId;
        private int order;
    }

}
