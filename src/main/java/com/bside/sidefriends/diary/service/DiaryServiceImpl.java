package com.bside.sidefriends.diary.service;

import com.bside.sidefriends.diary.domain.Diary;
import com.bside.sidefriends.diary.repository.DiaryRepository;
import com.bside.sidefriends.diary.service.dto.*;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.error.exception.PetNotFoundException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserNotFoundException;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final DiaryRepository petDiaryRepository;

    @Override
    public CreatePetDiaryResponseDto createPetDiary(Long petId, String username, CreatePetDiaryRequestDto createDiaryRequestDto) {

        // TODO: 하루에 한 건 작성 예외 추가

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(PetNotFoundException::new); // TODO: 펫 예외 생성자 수정 필요. IR.

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        Diary diary = Diary.builder()
                .pet(findPet)
                .user(findUser)
                .contents(createDiaryRequestDto.getContents())
                .build();

        petDiaryRepository.save(diary);

        return new CreatePetDiaryResponseDto(
                diary.getPet().getPetId(),
                getPetDiaryInfo.apply(diary)
        );
    }

    @Override
    public GetPetDiaryListResponseDto getPetDiaryList(Long petId) {
        List<Diary> findPetDiaries = petDiaryRepository.findAllByPetId(petId);

        List<PetDiaryInfo> petDiaryList = findPetDiaries.stream()
                .map(getPetDiaryInfo)
                .collect(Collectors.toList());

        return new GetPetDiaryListResponseDto(
                petDiaryList.size(),
                petDiaryList
        );
    }

    @Override
    public ModifyPetDiaryResponseDto modifyPetDiary(Long diaryId, String username, ModifyPetDiaryRequestDto modifyDiaryRequestDto) {

        Diary findDiary = petDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalStateException("한줄일기를 찾을 수 없습니다."));

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        if (findDiary.getUser() == null || !findDiary.getUser().getUserId().equals(findUser.getUserId())) {
            throw new IllegalStateException("자신이 작성한 한줄일기만 수정할 수 있습니다.");
        }

        Pet diaryPet = findDiary.getPet();
        if (diaryPet == null || diaryPet.isDeactivated() || diaryPet.isDeleted()) {
            throw new IllegalStateException("기록이 활성화된 펫이 아닙니다.");
        }

        findDiary.modify(modifyDiaryRequestDto.getContents());

        petDiaryRepository.save(findDiary);

        return new ModifyPetDiaryResponseDto(
                diaryPet.getPetId(),
                getPetDiaryInfo.apply(findDiary)
        );
    }

    @Override
    public DeletePetDiaryResponseDto deletePetDiary(Long diaryId) {
        return null;
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
