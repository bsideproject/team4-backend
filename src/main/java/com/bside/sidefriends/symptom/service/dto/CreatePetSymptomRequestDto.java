package com.bside.sidefriends.symptom.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CreatePetSymptomRequestDto {

    @NotNull
    private LocalDate date;

    @NotNull
    private List<String> symptoms;

}
