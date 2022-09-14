package com.bside.sidefriends.quick.service;


import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.error.exception.PetDeactivatedException;
import com.bside.sidefriends.pet.error.exception.PetNotFoundException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.quick.domain.Quick;
import com.bside.sidefriends.quick.domain.QuickDefault;
import com.bside.sidefriends.quick.domain.QuickHistory;
import com.bside.sidefriends.quick.repository.QuickHistoryRepository;
import com.bside.sidefriends.quick.repository.QuickRepository;
import com.bside.sidefriends.quick.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserMainPetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuickServiceImpl implements QuickService {

    private final QuickRepository quickRepository;
    private final QuickHistoryRepository quickHistoryRepository;
    private final PetRepository petRepository;

    // TODO-jh : Pet Create 할 때 최초 1회 호출되어야 합니다
    @Override
    public CreateQuickResponseDto createDefaultQuick(User user) {
        // Get Main Pet Info
        Long petId = user.getMainPetId();
        if (petId == null) throw new UserMainPetNotFoundException();

        Pet pet = petRepository.findByPetId(petId).orElseThrow(PetNotFoundException::new);
        if (pet.isDeactivated()) throw new PetDeactivatedException();


        // Get Quick Default Values
        List<QuickDefault> quickDefault = List.of(QuickDefault.values());

        // Create Default Quick
        List<Quick> quickEntityList = quickDefault.stream()
                .map(q -> Quick.builder()
                        .pet(pet)
                        .name(q.getName())
                        .total(q.getTotal())
                        .explanation("")
                        .order(q.getOrder())
                        .startedAt(LocalDateTime.of(0001,1,1,00,00,00))
                        .endedAt(LocalDateTime.of(9999,12,31,23,59,59))
                        .build())
                .collect(Collectors.toList());

        // Save Default Quick
        List<Quick> quickList = quickEntityList.stream()
                .map(quickRepository::save)
                .collect(Collectors.toList());

        // Generate Return DTO
        List<CreateQuickResponseDto.QuickDetail> quickDetailList = quickList.stream()
                .map(q -> new CreateQuickResponseDto.QuickDetail(
                        q.getQuickId(), q.getName(), 0, q.getTotal(), q.getExplanation(), q.getOrder()
                ))
                .collect(Collectors.toList());

        return new CreateQuickResponseDto(
                quickDetailList
        );
    }

    @Override
    public FindQuickByPetIdResponseDto findQuickByPetId(User user, LocalDate date) {
        // Get Main Pet Info
        Long petId = user.getMainPetId();
        if (petId == null) throw new UserMainPetNotFoundException();

        Pet pet = petRepository.findByPetId(petId).orElseThrow(PetNotFoundException::new);
        if (pet.isDeactivated()) throw new PetDeactivatedException();

        // Get Quick Data within Date
        // 유효한 날짜 범위인 Quick 정보만 가져와야 한다 (startedAt <= date < endedAt 인 퀵기록 데이터)
        List<Quick> quickList = quickRepository.findAllByPet(pet);
        List<FindQuickByPetIdResponseDto.QuickDetail> quickDetailList = quickList.stream()
                .map(q-> {
                    if (date.isAfter(q.getStartedAt().toLocalDate().minusDays(1)) && date.isBefore(q.getEndedAt().toLocalDate())) {
                        int count = quickHistoryRepository.findByQuickAndCreatedAt(q, date)
                                .map(quickHistory -> quickHistory.getCount())
                                .orElse(0);
                        return new FindQuickByPetIdResponseDto.QuickDetail(
                                q.getQuickId(), q.getName(), count, q.getTotal(), q.getExplanation(), q.getOrder());

                    } else {
                        return null;
                    }
                })
                .filter(quickDetail -> quickDetail != null)
                .collect(Collectors.toList());

        return new FindQuickByPetIdResponseDto(
                date,
                quickDetailList
        );
    }

    @Override
    public ChangeQuickResponseDto changeQuick(Long quickId, ChangeQuickRequestDto changeQuickRequestDto) {

        // Get Quick Info
        Quick beforeQuick = quickRepository.findById(quickId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 퀵기록 Id 입니다 : " + quickId));

        // Create afterQuick
        Quick afterQuick = Quick.builder()
                .pet(beforeQuick.getPet())
                .name(changeQuickRequestDto.getName())
                .total(changeQuickRequestDto.getTotal())
                .explanation(changeQuickRequestDto.getExplanation())
                .order(beforeQuick.getOrder())
                .startedAt(LocalDateTime.now())
                .endedAt(beforeQuick.getEndedAt())
                .build();
        Quick quick = quickRepository.save(afterQuick);

        // Update beforeQuick's EndedAt as now()
        beforeQuick.changeEndedAt(LocalDateTime.now());
        quickRepository.save(beforeQuick);

        return new ChangeQuickResponseDto(
                quick.getQuickId(), quick.getName(), quick.getTotal(), quick.getExplanation()
        );
    }

    @Override
    public ChangeQuickOrderResponseDto changeQuickOrder(User user, ChangeQuickOrderRequestDto changeQuickOrderRequestDto) {
        // Get Main Pet Info
        Long petId = user.getMainPetId();
        if (petId == null) throw new UserMainPetNotFoundException();

        Pet pet = petRepository.findByPetId(petId).orElseThrow(PetNotFoundException::new);
        if (pet.isDeactivated()) throw new PetDeactivatedException();

        // Change Quick Order
        changeQuickOrderRequestDto.getQuickOrderList().stream()
                .forEach(quickOrder -> {
                    Quick beforeQuick = quickRepository.findById(quickOrder.getQuickId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 퀵기록 Id 입니다 : " + quickOrder.getQuickId()));

                    // Create afterQuick
                    Quick afterQuick = Quick.builder()
                            .pet(beforeQuick.getPet())
                            .name(beforeQuick.getName())
                            .total(beforeQuick.getTotal())
                            .explanation(beforeQuick.getExplanation())
                            .order(quickOrder.getOrder())
                            .startedAt(LocalDateTime.now())
                            .endedAt(beforeQuick.getEndedAt())
                            .build();
                    quickRepository.save(afterQuick);

                    // Update beforeQuick's EndedAt as now()
                    beforeQuick.changeEndedAt(LocalDateTime.now());
                    quickRepository.save(beforeQuick);

                });

        // Generate Return DTO
        LocalDate date = LocalDate.now();
        List<ChangeQuickOrderResponseDto.QuickDetail> quickDetailList = quickRepository.findAllByPet(pet).stream()
                .map(q -> {
                    if (date.isAfter(q.getStartedAt().toLocalDate().minusDays(1)) && date.isBefore(q.getEndedAt().toLocalDate())) {
                        int count = quickHistoryRepository.findByQuickAndCreatedAt(q, date)
                                .map(quickHistory -> quickHistory.getCount())
                                .orElse(0);
                        return new ChangeQuickOrderResponseDto.QuickDetail(
                                q.getQuickId(), q.getName(), count, q.getTotal(), q.getExplanation(), q.getOrder());

                    } else {
                        return null;
                    }
                })
                .filter(quickDetail -> quickDetail != null)
                .collect(Collectors.toList());

        return new ChangeQuickOrderResponseDto(
                quickDetailList
        );
    }

    @Override
    public ChangeQuickCountResponseDto changeQuickCount(Long quickId) {
        Quick quick = quickRepository.findById(quickId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 퀵기록 입니다."));


        // Change Quick Count +1
        LocalDate now = LocalDate.now();
        quickHistoryRepository.findByQuickAndCreatedAt(quick, now)
                .ifPresentOrElse(
                        qhist -> { // If Present, count +1
                            if (qhist.getCount() < quick.getTotal()) {
                                qhist.increaseCount();
                                quickHistoryRepository.save(qhist);
                            } else {
                                throw new IllegalStateException("퀵기록 실행횟수를 초과하였습니다.");
                            }
                        },
                        () -> { // If not Present, create new
                            QuickHistory quickHistoryEntity = QuickHistory.builder()
                                    .quick(quick)
                                    .count(1)
                                    .createdAt(now)
                                    .build();
                            quickHistoryRepository.save(quickHistoryEntity);
                        });

        QuickHistory quickHistory = quickHistoryRepository.findByQuick(quick).get();
        return new ChangeQuickCountResponseDto(
                quickHistory.getQuick().getQuickId(),
                quickHistory.getQuick().getName(),
                quickHistory.getCount(),
                quickHistory.getQuick().getTotal()
        );
    }
}
