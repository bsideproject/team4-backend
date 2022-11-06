package com.bside.sidefriends.diary.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ApiModel(value = "한줄일기 정보")
public class PetDiaryInfo {

    @ApiModelProperty(value = "한줄일기 id", example = "1")
    private Long diaryId;

    @ApiModelProperty(value = "한줄일기 작성자 id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "한줄일기 작성자명", example = "사이드프렌즈")
    private String userName;

    @ApiModelProperty(value = "한줄일기 작성자 삭제 여부", example = "false")
    private boolean isDeleted;

    @ApiModelProperty(value = "한줄일기 내용")
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "한줄일기 작성 날짜")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "한줄일기 마지막 수정 날짜")
    private LocalDateTime lastModifiedAt;
}
