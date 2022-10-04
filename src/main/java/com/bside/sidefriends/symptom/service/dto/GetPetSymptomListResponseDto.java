package com.bside.sidefriends.symptom.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPetSymptomListResponseDto {

    private Long petId;

    private Long symptomId;

    private List<String> symptoms;
}
