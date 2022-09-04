package com.bside.sidefriends.pet.domain;

public enum PetBloodType {

    // TODO: 혈액형 관리 시 필요할 수 있어 임시로 생성. IR.

    DOG_DEA1("Dog", "DEA1"),
    DOG_DEA2("Dog", "DEA2"),
    CAT_A("Cat", "A"),
    CAT_B("Cat", "B"),
    CAT_AB("Cat", "AB")
    ;

    final String petType;
    final String bloodType;

    PetBloodType(String petType, String bloodType) {
        this.petType = petType;
        this.bloodType = bloodType;
    }

}
