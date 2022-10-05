package com.bside.sidefriends.symptom.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ApiModel(value = "펫 이상징후 기록 수정 요청")
public class ModifyPetSymptomRequestDto {

    @ApiModelProperty(value = "펫 이상징후 리스트",
            example = "[\n" +
                "        \"털이 많이 빠져요.\",\n" +
                "        \"숨쉬는 걸 어렵거나 괴로워해요.\"\n" +
                "    ]",
            required = true
    )
    @NotNull
    List<String> symptoms;
}
