package com.bside.sidefriends.quick.domain;

import lombok.Getter;

@Getter
public enum QuickDefault {

    FOOD("사료주기", 3, 1),
    SNACK("간식주기", 3, 2),
    WALK("산책하기", 1, 3),
    TOOTH("양치하기", 1, 4);


    private String name;
    private int total;
    private int order;

    private QuickDefault(String name, int total, int order) {
        this.name = name;
        this.total = total;
        this.order = order;
    }

}
