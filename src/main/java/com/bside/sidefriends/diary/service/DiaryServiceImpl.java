package com.bside.sidefriends.diary.service;

import com.bside.sidefriends.diary.domain.Diary;
import com.bside.sidefriends.diary.exception.DiaryCreateLimitedException;
import com.bside.sidefriends.diary.exception.DiaryDeleteNotAllowedException;
import com.bside.sidefriends.diary.exception.DiaryModifyNotAllowedException;
import com.bside.sidefriends.diary.exception.DiaryNotFoundException;
import com.bside.sidefriends.diary.repository.DiaryRepository;
import com.bside.sidefriends.diary.service.dto.*;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.exception.PetNotFoundException;
import com.bside.sidefriends.pet.exception.PetDeactivatedException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserNotFoundException;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatePetDiaryResponseDto createPetDiary(Long petId, String username, CreatePetDiaryRequestDto createDiaryRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalse(petId).orElseThrow(PetNotFoundException::new);
        if (findPet.isDeactivated()) {
            throw new PetDeactivatedException();
        }

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);
        // TODO: 사용자 혹은 가족 그룹 펫인지 확인 필요

        if (diaryRepository.existsByUserUsername(username)) {
            throw new DiaryCreateLimitedException();
        }

        Diary diary = Diary.builder()
                .pet(findPet)
                .user(findUser)
                .contents(createDiaryRequestDto.getContents())
                .build();

        diaryRepository.save(diary);

        return new CreatePetDiaryResponseDto(
                diary.getPet().getPetId(),
                getPetDiaryInfo.apply(diary)
        );
    }

    @Override
    public GetPetDiaryListResponseDto getPetDiaryList(Long petId) {
        List<Diary> findPetDiaries = diaryRepository.findAllByPetId(petId);

        List<PetDiaryInfo> petDiaryList = findPetDiaries.stream()
                .map(getPetDiaryInfo)
                .collect(Collectors.toList());

        return new GetPetDiaryListResponseDto(
                petDiaryList.size(),
                petDiaryList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyPetDiaryResponseDto modifyPetDiary(Long diaryId, String username, ModifyPetDiaryRequestDto modifyDiaryRequestDto) {

        Diary findDiary = diaryRepository.findById(diaryId).orElseThrow(DiaryNotFoundException::new);

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        User writer = findDiary.getUser();
        if (writer == null || !writer.getUserId().equals(findUser.getUserId())) {
            throw new DiaryModifyNotAllowedException();
        }

        Pet diaryPet = findDiary.getPet();
        if (diaryPet == null || diaryPet.isDeactivated() || diaryPet.isDeleted()) {
            throw new PetDeactivatedException();
        }

        findDiary.modify(modifyDiaryRequestDto.getContents());

        diaryRepository.save(findDiary);

        return new ModifyPetDiaryResponseDto(
                diaryPet.getPetId(),
                getPetDiaryInfo.apply(findDiary)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeletePetDiaryResponseDto deletePetDiary(Long diaryId, String username) {

        Diary findDiary= diaryRepository.findById(diaryId).orElseThrow(DiaryNotFoundException::new);

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        User writer = findDiary.getUser();
        if (writer == null || !writer.getUserId().equals(findUser.getUserId())) {
            throw new DiaryDeleteNotAllowedException();
        }

        diaryRepository.delete(findDiary);

        return new DeletePetDiaryResponseDto(diaryId);
    }

    Function<Diary, PetDiaryInfo> getPetDiaryInfo = diary -> {
        String writer;
        User user = diary.getUser();
        if (user == null || user.isDeleted()) {
            writer = null;
        } else {
            writer = user.getName();
        }
        return new PetDiaryInfo(diary.getDiaryId(), writer, diary.getContents(), diary.getUpdatedAt());
    };
}
