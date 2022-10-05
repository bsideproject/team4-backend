package com.bside.sidefriends.symptom.service;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.exception.PetNotFoundException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.symptom.domain.Symptom;
import com.bside.sidefriends.symptom.domain.SymptomCode;
import com.bside.sidefriends.symptom.exception.SymptomExistsException;
import com.bside.sidefriends.symptom.exception.SymptomNotFoundException;
import com.bside.sidefriends.symptom.exception.SymptomNotSupportedException;
import com.bside.sidefriends.symptom.repository.SymptomRepository;
import com.bside.sidefriends.symptom.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    private final PetRepository petRepository;
    private final SymptomRepository symptomRepository;

    @Override
    public CreatePetSymptomResponseDto createPetSymptom(Long petId, CreatePetSymptomRequestDto createPetSymptomRequestDto) {

        Pet findPet = petRepository.findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(petId)
                .orElseThrow(PetNotFoundException::new);

        if (symptomRepository.findByPetPetIdAndDate(petId, createPetSymptomRequestDto.getDate()).isPresent()) {
            throw new SymptomExistsException();
        }

        List<String> symptomDescriptionList = createPetSymptomRequestDto.getSymptoms();
        String petSymptoms = buildSymptomList(symptomDescriptionList);

        Symptom symptomEntity = Symptom.builder()
                .pet(findPet)
                .symptomList(petSymptoms)
                .date(createPetSymptomRequestDto.getDate())
                .build();

        symptomRepository.save(symptomEntity);

        return new CreatePetSymptomResponseDto(
                symptomEntity.getPet().getPetId(),
                symptomEntity.getSymptomId(),
                symptomEntity.getDate(),
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
                .date(findSymptom.getDate())
                .symptomList(petSymptoms)
                .build();

        symptomRepository.save(symptomEntity);

        return new ModifyPetSymptomResponseDto(
                symptomEntity.getPet().getPetId(),
                symptomEntity.getSymptomId(),
                symptomEntity.getDate(),
                symptomDescriptionList
        );

    }

    @Override
    public GetPetSymptomListResponseDto getPetSymptomList(Long petId, LocalDate date) {

        Symptom findSymptom = symptomRepository.findByPetPetIdAndDate(petId, date)
                .orElseThrow(SymptomNotFoundException::new);

        List<String> symptomDescriptionList = Stream.of(findSymptom.getSymptomList().split(", "))
                .map(symptom -> SymptomCode.valueOf(symptom).getDescription())
                .collect(Collectors.toList());

        return new GetPetSymptomListResponseDto(
                findSymptom.getPet().getPetId(),
                findSymptom.getSymptomId(),
                findSymptom.getDate(),
                symptomDescriptionList
        );
    }

    // TODO: 좋은 방법일지 고민 필요. IR.
    private String buildSymptomList(List<String> symptomDescriptionList) {

        List<String> symptoms = new ArrayList<>();

        for (String symptomDescription: symptomDescriptionList) {
            String symptomCodeString = SymptomCode.of(symptomDescription);
            if (symptomCodeString == null) {
                throw new SymptomNotSupportedException(symptomDescription);
            }
            symptoms.add(symptomCodeString);
        }

        return symptoms.toString().replace("[", "").replace("]", "");

    }
}
