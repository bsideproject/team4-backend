package com.bside.sidefriends.symptom.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@ApiModel(value = "펫 이상징후 기록 생성")
public class CreatePetSymptomRequestDto {

    @NotNull
    @ApiModelProperty(value = "이상징후 기록 날짜", example = "2022-10-04", required = true)
    private LocalDate date;

    @NotNull
    @ApiModelProperty(
            value = "이상징후 설명 리스트",
            example = "[\n" +
            "        \"열이 나요.\",\n" +
            "        \"털이 많이 빠져요.\",\n" +
            "        \"숨쉬는 걸 어렵거나 괴로워해요.\"\n" +
            "    ]",
            required = true)
    private List<String> symptoms;

}
