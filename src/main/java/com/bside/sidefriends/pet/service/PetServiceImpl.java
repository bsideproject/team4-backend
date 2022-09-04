package com.bside.sidefriends.pet.service;

import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.domain.PetGender;
import com.bside.sidefriends.pet.domain.PetShareScope;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.pet.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserNotFoundException;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    // TODO: UserRepository, FamilyRepository 구현 변경 반영 필요
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;


    @Override
    public CreatePetResponseDto createPet(String username, CreatePetRequestDto createPetRequestDto) {

        User findUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        // TODO: 펫 중복 체크 필요성 검토

        // 펫 도메인 객체 생성
        Pet petEntity = Pet.builder()
                .name(createPetRequestDto.getName())
                .gender(createPetRequestDto.getGender())
                .breed(createPetRequestDto.getBreed())
                .birthday(createPetRequestDto.getBirthday())
                .age(createPetRequestDto.getAge())
                .adoptionDate(createPetRequestDto.getAdoptionDate())
                .animalRegistrationNumber(createPetRequestDto.getAnimalRegistrationNumber())
                .shareScope(PetShareScope.PRIVATE) // 펫 생성 시 기본 개인 펫 설정
                .isDeactivated(false) // 펫 생성 시 비활성화 여부 기본값 false
                .isDeleted(false) // 펫 생성 시 삭제 여부 기본값 false
                .build();

        // 펫 저장
        petRepository.save(petEntity);

        // 사용자 펫 추가
        findUser.addPet(petEntity);
        userRepository.save(findUser);


        return CreatePetResponseDto.builder()
                .petId(petEntity.getPetId())
                .shareScope(petEntity.getShareScope())
                .name(petEntity.getName())
                .breed(petEntity.getBreed())
                .gender(petEntity.getGender())
                .birthday(petEntity.getBirthday())
                .age(petEntity.getAge())
                .animalRegistrationNumber(petEntity.getAnimalRegistrationNumber())
//                .userId(findUser.getUserId()) // 변경한 코드
                .user(findUser) // 적용한 코드
                .build();
    }

    @Override
    public FindPetResponseDto findPet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new IllegalStateException("이미 삭제된 펫입니다."));

        return FindPetResponseDto.builder()
                .petId(findPet.getPetId())
                .name(findPet.getName())
                .shareScope(findPet.getShareScope())
                .gender(findPet.getGender())
                .breed(findPet.getBreed())
                .birthday(findPet.getBirthday())
                .age(findPet.getAge())
                .adoptionDate(findPet.getAdoptionDate())
                .animalRegistrationNumber(findPet.getAnimalRegistrationNumber())
                .build();
    }

    @Override
    public ModifyPetResponseDto modifyPet(Long petId, ModifyPetRequestDto modifyPetRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new IllegalStateException("이미 삭제된 펫입니다."));

        if (modifyPetRequestDto.isEmpty()) {
            throw new IllegalStateException("수정할 펫 정보가 없습니다.");
        }

        // TODO: 필드 수정 방식 리팩토링
//        if (modifyPetRequestDto.getName() != null) {
//            findPet.setName(modifyPetRequestDto.getName());
//        }
        Optional.ofNullable(modifyPetRequestDto.getName()).ifPresent(findPet::setName);
        Optional.ofNullable(modifyPetRequestDto.getGender()).ifPresent(findPet::setGender);
        Optional.ofNullable(modifyPetRequestDto.getBreed()).ifPresent(findPet::setBreed);
        Optional.ofNullable(modifyPetRequestDto.getBirthday()).ifPresent(findPet::setBirthday);
        Optional.ofNullable(modifyPetRequestDto.getAge()).ifPresent(findPet::setAge);
        Optional.ofNullable(modifyPetRequestDto.getAdoptionDate()).ifPresent(findPet::setAdoptionDate);
        Optional.ofNullable(modifyPetRequestDto.getAnimalRegistrationNumber()).ifPresent(findPet::setAnimalRegistrationNumber);

        petRepository.save(findPet);

        return ModifyPetResponseDto.builder()
                .petId(findPet.getPetId())
                .shareScope(findPet.getShareScope())
                .name(findPet.getName())
                .breed(findPet.getBreed())
                .gender(findPet.getGender())
                .birthday(findPet.getBirthday())
                .adoptionDate(findPet.getAdoptionDate())
                .age(findPet.getAge())
                .animalRegistrationNumber(findPet.getAnimalRegistrationNumber())
                .build();
    }

    @Override
    public DeactivatePetResponseDto deactivatePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(() -> new IllegalStateException("삭제되었거나 비활성화된 펫입니다."));

        findPet.deactivate();
        petRepository.save(findPet);

        return new DeactivatePetResponseDto(
                findPet.getPetId(),
                findPet.isDeactivated()
        );
    }

    @Override
    public ActivatePetResponseDto activatePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedTrue(petId)
                .orElseThrow(() -> new IllegalStateException("삭제되었거나 이미 활성 상태인 펫입니다."));

        findPet.activate();
        petRepository.save(findPet);

        return new ActivatePetResponseDto(
                findPet.getPetId(),
                findPet.isDeactivated()
        );
    }

    @Override
    public DeletePetResponseDto deletePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new IllegalStateException("이미 삭제된 펫입니다."));

        findPet.delete();
        petRepository.save(findPet);

        return new DeletePetResponseDto(
                findPet.getPetId(),
                findPet.isDeleted()
        );
    }
}
