package com.bside.sidefriends.quick.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ChangeQuickResponseDto {

    private Long quickId;

    private String name;

    private int total;

    private String explanation;
}
