package com.bside.sidefriends.pet.service.dto;

import com.bside.sidefriends.pet.domain.PetGender;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class ModifyPetRequestDto {

    @Nullable
    private String name;

    @Nullable
    private PetGender gender;

    @Nullable
    private String breed;

    @Nullable
    private String birthday;

    @Nullable
    private Long age;

    @Nullable
    private String adoptionDate;

    @Nullable
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
