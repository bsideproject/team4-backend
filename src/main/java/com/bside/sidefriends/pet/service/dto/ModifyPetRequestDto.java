package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@ApiModel(value = "가족 그룹 구성원 추가",
        description = "반려동물 정보 수정을 위해 적어도 하나의 필드 값 필요")
public class ModifyPetRequestDto {

    @Nullable
    @ApiModelProperty(value = "반려동물 이름")
    private String name;

    @Nullable
    @ApiModelProperty(value = "반려동물 성별")
    private PetGender gender;

    @Nullable
    @ApiModelProperty(value = "반려동물 품종")
    private String breed;

    @Nullable
    @ApiModelProperty(value = "반려동물 생일")
    private String birthday;

    @Nullable
    @ApiModelProperty(value = "반려동물 나이")
    private Long age;

    @Nullable
    @ApiModelProperty(value = "반려동물 입양일")
    private String adoptionDate;

    @Nullable
    @ApiModelProperty(value = "반려동물 동물등록번호")
    private String animalRegistrationNumber;

    // 수정할 필드가 있는지 확인하기 위한 편의 메서드
    // TODO: valid 어노테이션을 통해 잡는 것이 나을지 검토 필요. IR.
    public boolean isEmpty() {
        return (this.name == null &&
                this.gender == null &&
                this.breed == null &&
                this.birthday == null &&
                this.age == null &&
                this.adoptionDate == null &&
                this.animalRegistrationNumber == null);
    }

}
