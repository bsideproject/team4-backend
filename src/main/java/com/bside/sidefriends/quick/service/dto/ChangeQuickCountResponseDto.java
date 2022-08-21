package com.bside.sidefriends.quick.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeQuickCountResponseDto {

        private Long quickId;
        private String name;
        private int count;
        private int total;
}
