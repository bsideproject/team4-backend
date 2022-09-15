package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import com.bside.sidefriends.pet.domain.PetShareScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
public class FindPetResponseDto {

    @NotNull
    @ApiModelProperty(value = "반려동물 id", example = "1", required = true)
    private Long petId;

    @NotNull
    @ApiModelProperty(value = "회원 id", example = "1", required = true)
    private Long userId; // 펫 소유 사용자

    @NotNull
    @ApiModelProperty(value = "반려동물 이름", example = "멍멍이", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "반려동물 공유 범위", notes = "공유 펫인 경우 FAMILY", required = true)
    private PetShareScope shareScope;

    // TODO: 공유펫 가족 id
    private Long familyId; // 펫 공유 가족

    @ApiModelProperty(value = "반려동물 성별")
    private PetGender gender;

    @ApiModelProperty(value = "반려동물 품종", example = "강아지")
    private String breed;

    @ApiModelProperty(value = "반려동물 생일")
    private String birthday;

    @ApiModelProperty(value = "반려동물 나이")
    private Long age;

    @ApiModelProperty(value = "반려동물 입양일")
    private String adoptionDate;

    @ApiModelProperty(value = "반려동물 동물등록번호")
    private String animalRegistrationNumber;

    // TODO: 펫 이미지
}
