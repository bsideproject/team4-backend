package com.bside.sidefriends.pet.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.users.exception.*;
import com.bside.sidefriends.family.exception.*;
import com.bside.sidefriends.pet.exception.*;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.domain.PetShareScope;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.pet.service.dto.*;
import com.bside.sidefriends.quick.service.QuickService;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    private final QuickService quickService;

    // TODO: 전체적으로 사용자 소유 펫인지 확인 필요

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatePetResponseDto createUserPet(String username, CreatePetRequestDto createPetRequestDto) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

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
                .petImage(null)
                .build();

        Pet pet = petRepository.save(petEntity);

        // 사용자 펫 추가 및 최초 등록 시 대표펫 설정
        findUser.addPet(petEntity);
        if (findUser.getMainPetId() == null) {
            findUser.setMainPet(petEntity.getPetId());
        }
        userRepository.save(findUser);

        // 펫 최초 생성 시, Default 퀵 정보 생성
        quickService.createDefaultQuick(pet);

        return CreatePetResponseDto.builder()
                .petId(petEntity.getPetId())
                .shareScope(petEntity.getShareScope())
                .name(petEntity.getName())
                .breed(petEntity.getBreed())
                .gender(petEntity.getGender())
                .birthday(petEntity.getBirthday())
                .age(petEntity.getAge())
                .animalRegistrationNumber(petEntity.getAnimalRegistrationNumber())
                .userId(petEntity.getUser().getUserId())
                .petImageUrl(petEntity.getImageUrlInfo())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SharePetResponseDto sharePet(String username, Long petId) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        Long familyId = findUser.getFamilyIdInfo();
        if (findUser.getFamily() == null) {
            throw new PetShareFailException("펫을 공유할 가족 그룹이 없습니다.");
        }

        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId)
                .orElseThrow(FamilyNotFoundException::new);

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

        if (!findPet.getUser().getUserId().equals(petId)) {
            throw new PetShareFailException("사용자 소유의 펫이 아닙니다.");
        }

        if (findPet.getFamily() != null) {
            throw new PetShareFailException("이미 공유된 펫입니다.");
        }

        findFamily.addPet(findPet); // 가족에 펫 추가
        findPet.setFamily(findFamily); // 펫에 가족 설정
        petRepository.save(findPet);
        familyRepository.save(findFamily);

        return new SharePetResponseDto(
                findPet.getPetId(),
                findPet.getShareScope(),
                findPet.getFamilyIdInfo()
        );
    }

    @Override
    public FindPetResponseDto findPet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

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
                .userId(findPet.getUser().getUserId())
                .familyId(findPet.getFamilyIdInfo())
                .petImageUrl(findPet.getImageUrlInfo())
                .build();
    }

    @Override
    public FindAllPetResponseDto findAllPets(String username) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        // HACK: 아주 좋지 않은 방법. IR.
        // TODO: join 사용 쿼리로 변경 후 리팩토링. IR.
        Long userId = findUser.getUserId();
        Long familyId = findUser.getFamilyIdInfo();

        List<Pet> userPets = petRepository.findAllByUserIdAndIsDeletedFalse(userId);
        Set<Pet> setOfPets = new LinkedHashSet<>(userPets);

        if (familyId != null) {
            List<Pet> familyPets = petRepository.findAllByFamilyIdAndIsDeletedFalse(familyId);
            setOfPets.addAll(familyPets);
        }

        // TODO: 리팩토링. IR.
        List<PetInfo> petList = setOfPets.stream()
                .map(getPetInfo)
                .collect(Collectors.toList());

        return new FindAllPetResponseDto(
                findUser.getUserId(),
                findUser.getMainPetId(),
                petList.size(),
                petList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyPetResponseDto modifyPet(Long petId, ModifyPetRequestDto modifyPetRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

        if (modifyPetRequestDto.isEmpty()) {
            throw new PetModifyFailException("수정할 펫 정보가 없습니다.");
        }

        // TODO: 필드 수정 방식 리팩토링
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
                .petImageUrl(findPet.getImageUrlInfo())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeactivatePetResponseDto deactivatePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(() -> new PetNotFoundException("이미 삭제되었거나 비활성화된 펫입니다."));

        findPet.deactivate();
        petRepository.save(findPet);

        return new DeactivatePetResponseDto(
                findPet.getPetId(),
                findPet.isDeactivated()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivatePetResponseDto activatePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedTrue(petId)
                .orElseThrow(() -> new PetNotFoundException("이미 삭제되었거나 비활성화된 펫입니다."));

        findPet.activate();
        petRepository.save(findPet);

        return new ActivatePetResponseDto(
                findPet.getPetId(),
                findPet.isDeactivated()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeletePetResponseDto deletePet(Long petId) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

        // TODO: 대표펫 삭제 시, 대표펫이 없는 경우 처리 방법 논의 필요. IR.

        findPet.delete();
        petRepository.save(findPet);

        return new DeletePetResponseDto(
                findPet.getPetId(),
                findPet.isDeleted()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateMainPetResponseDto updateMainPet(String username, UpdateMainPetRequestDto updateMainPetRequestDto) {

        Long petId = updateMainPetRequestDto.getMainPetId();

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(() -> new PetNotFoundException("이미 삭제되었거나 비활성화된 펫입니다."));

        // TODO: 사용자 혹은 사용자 가족 그룹의 펫이 아닌 경우 예외 처리

        findUser.setMainPet(petId);
        userRepository.save(findUser);

        return new UpdateMainPetResponseDto(
                findUser.getUserId(),
                findUser.getMainPetId(),
                getPetInfo.apply(findPet)
        );
    }

    private static final Function<Pet, PetInfo> getPetInfo =
            pet -> new PetInfo(
                    pet.getPetId(),
                    pet.getName(),
                    pet.getShareScope(),
                    pet.getUser().getUserId(),
                    pet.getFamilyIdInfo(),
                    pet.getGender(),
                    pet.getBreed(),
                    pet.getBirthday(),
                    pet.getAge(),
                    pet.getAdoptionDate(),
                    pet.getAnimalRegistrationNumber(),
                    pet.getImageUrlInfo()
            );
}
