package com.bside.sidefriends.quick.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ChangeQuickRequestDto {

    @NotNull
    private String name;

    @NotNull
    private int total;

    private String explanation;
}
