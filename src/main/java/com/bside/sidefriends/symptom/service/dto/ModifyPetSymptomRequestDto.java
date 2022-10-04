package com.bside.sidefriends.symptom.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ModifyPetSymptomRequestDto {

    @NotNull
    String date;

    @NotNull
    List<String> symptoms;
}
