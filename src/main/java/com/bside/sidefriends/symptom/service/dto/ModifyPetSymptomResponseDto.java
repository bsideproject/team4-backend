package com.bside.sidefriends.symptom.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ModifyPetSymptomResponseDto {

    private Long petId;

    private Long petSymptomId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private List<String> symptoms;

}
