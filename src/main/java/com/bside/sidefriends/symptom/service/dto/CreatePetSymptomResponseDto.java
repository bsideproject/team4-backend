package com.bside.sidefriends.symptom.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ApiModel(value = "펫 이상징후 기록 생성 응답")
public class CreatePetSymptomResponseDto {

    @ApiModelProperty(value = "펫 id")
    private Long petId;

    @ApiModelProperty(value = "펫 이상징후 기록 id")
    private Long petSymptomId;

    @ApiModelProperty(value = "펫 이상징후 기록 날짜")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ApiModelProperty(value = "펫 이상징후 리스트")
    private List<String> symptoms;
}
