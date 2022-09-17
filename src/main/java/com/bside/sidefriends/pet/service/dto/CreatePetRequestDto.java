package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "반려동물 생성")
public class CreatePetRequestDto {

    @NotNull
    @ApiModelProperty(value = "반려동물 이름", required = true)
    private String name;

    @ApiModelProperty(value = "반려동물 성별")
    private PetGender gender;

    @ApiModelProperty(value = "반려동물 품종")
    private String breed;

    @ApiModelProperty(value = "반려동물 생일")
    private String birthday;

    @ApiModelProperty(value = "반려동물 나이")
    private Long age;

    @ApiModelProperty(value = "반려동물 입양일", notes = "처음 만난 날")
    private String adoptionDate;

    @ApiModelProperty(value = "동물등록번호")
    private String animalRegistrationNumber;

}
