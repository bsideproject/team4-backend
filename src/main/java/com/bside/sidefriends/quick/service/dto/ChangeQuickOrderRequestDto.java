package com.bside.sidefriends.quick.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class ChangeQuickOrderRequestDto {

    @Getter
    private List<QuickOrder> quickOrderList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuickOrder {
        @ApiModelProperty(value = "퀵 Id", example = "1")
        private Long quickId;
        @ApiModelProperty(value = "퀵 순서", example = "퀵 순서")
        private int order;
    }

}
