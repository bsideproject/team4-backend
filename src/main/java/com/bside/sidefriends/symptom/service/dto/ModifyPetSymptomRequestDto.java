package com.bside.sidefriends.symptom.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class ModifyPetSymptomRequestDto {

    @NotNull
    LocalDate date;

    @NotNull
    List<String> symptoms;
}
