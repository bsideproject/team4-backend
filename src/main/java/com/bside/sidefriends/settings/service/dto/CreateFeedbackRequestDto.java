package com.bside.sidefriends.settings.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "피드백 생성")
public class CreateFeedbackRequestDto {

    @NotNull
    @Size(min = 1, message = "피드백 내용은 1자 이상이어야 합니다.")
    @ApiModelProperty(value = "피드백 내용", example="펫하루에 대한 피드백입니다", required = true)
    private String contents;

}
