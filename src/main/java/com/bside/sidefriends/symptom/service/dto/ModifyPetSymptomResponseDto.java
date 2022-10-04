package com.bside.sidefriends.symptom.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ModifyPetSymptomResponseDto {

    private Long petId;

    private Long petSymptomId;

    private String date;

    private List<String> symptoms;

}
