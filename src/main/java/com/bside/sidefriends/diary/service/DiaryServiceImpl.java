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

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public CreatePetDiaryResponseDto createPetDiary(Long petId, String username, CreatePetDiaryRequestDto createDiaryRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(PetNotFoundException::new); // TODO: 펫 예외 생성자 수정 필요. IR.

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(UserNotFoundException::new);

        Diary diary = Diary.builder()
                .pet(findPet)
                .user(findUser)
                .contents(createDiaryRequestDto.getContents())
                .build();

        diaryRepository.save(diary);

        return new CreatePetDiaryResponseDto(
                diary.getDiaryId(),
                diary.getPet().getPetId(),
                diary.getUser().getName(),
                diary.getContents()
        );
    }

    @Override
    public GetPetDiaryResponseDto getPetDiaryList(Long petId) {
        return null;
    }

    @Override
    public ModifyPetDiaryResponseDto modifyPetDiary(Long petId, ModifyPetDiaryRequestDto modifyDiaryRequestDto) {
        return null;
    }

    @Override
    public DeletePetDiaryResponseDto deletePetDiary(Long diaryId) {
        return null;
    }
}
