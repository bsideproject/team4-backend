package com.bside.sidefriends.symptom.service;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.exception.PetNotFoundException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.symptom.domain.Symptom;
import com.bside.sidefriends.symptom.domain.SymptomCode;
import com.bside.sidefriends.symptom.exception.SymptomNotFoundException;
import com.bside.sidefriends.symptom.exception.SymptomNotSupportedException;
import com.bside.sidefriends.symptom.repository.SymptomRepository;
import com.bside.sidefriends.symptom.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    private final PetRepository petRepository;
    private final SymptomRepository symptomRepository;

    @Override
    public CreatePetSymptomResponseDto createPetSymptom(Long petId, CreatePetSymptomRequestDto createPetSymptomRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

        List<String> symptomDescriptionList = createPetSymptomRequestDto.getSymptoms();
        String petSymptoms = buildSymptomList(symptomDescriptionList);

        Symptom symptomEntity = Symptom.builder()
                .pet(findPet)
                .symptomList(petSymptoms)
                .build();

        symptomRepository.save(symptomEntity);

        return new CreatePetSymptomResponseDto(
                symptomEntity.getPet().getPetId(),
                symptomEntity.getSymptomId(),
                symptomDescriptionList
        );
    }

    @Override
    public ModifyPetSymptomResponseDto modifyPetSymptom(Long symptomId, ModifyPetSymptomRequestDto modifyPetSymptomRequestDto) {

        Symptom findSymptom = symptomRepository.findById(symptomId)
                .orElseThrow(SymptomNotFoundException::new);

        List<String> symptomDescriptionList = modifyPetSymptomRequestDto.getSymptoms();
        String petSymptoms = buildSymptomList(symptomDescriptionList);

        Symptom symptomEntity = Symptom.builder()
                .pet(findSymptom.getPet())
                .symptomList(petSymptoms)
                .build();

        symptomRepository.save(symptomEntity);

        return new ModifyPetSymptomResponseDto(
                symptomEntity.getPet().getPetId(),
                symptomEntity.getSymptomId(),
                symptomDescriptionList
        );

    }

    @Override
    public GetPetSymptomListResponseDto getPetSymptomList(Long petId, LocalDate date) {

        Symptom findSymptom = symptomRepository.findByPetPetIdAndDate(petId, date)
                .orElseThrow(SymptomNotFoundException::new);

        List<String> symptomDescriptionList = Arrays.asList(findSymptom.getSymptomList()).stream()
                .map(symptom -> SymptomCode.valueOf(symptom).getDescription())
                .collect(Collectors.toList());

        return new GetPetSymptomListResponseDto(
                findSymptom.getPet().getPetId(),
                findSymptom.getSymptomId(),
                symptomDescriptionList
        );
    }

    private String buildSymptomList(List<String> symptomDescriptionList) {

        StringBuffer sb = new StringBuffer();

        for (String symptomDescription: symptomDescriptionList) {
            String symptomCodeString = SymptomCode.of(symptomDescription);
            if (symptomCodeString == null) {
                throw new SymptomNotSupportedException(symptomDescription);
            }
            sb.append(symptomCodeString);
        }

        return sb.toString();

    }
}
