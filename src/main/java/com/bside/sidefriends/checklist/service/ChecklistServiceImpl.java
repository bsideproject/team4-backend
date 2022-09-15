package com.bside.sidefriends.checklist.service;

import com.bside.sidefriends.checklist.domain.Checklist;
import com.bside.sidefriends.checklist.domain.ChecklistHistory;
import com.bside.sidefriends.checklist.domain.ChecklistMeta;
import com.bside.sidefriends.checklist.error.exception.ChecklistMetaNotFoundException;
import com.bside.sidefriends.checklist.error.exception.ChecklistNotFoundException;
import com.bside.sidefriends.checklist.repository.ChecklistHistoryRepository;
import com.bside.sidefriends.checklist.repository.ChecklistMetaRepository;
import com.bside.sidefriends.checklist.repository.ChecklistRepository;
import com.bside.sidefriends.checklist.service.dto.*;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.pet.error.exception.PetNotFoundException;
import com.bside.sidefriends.pet.error.exception.PetDeactivatedException;
import com.bside.sidefriends.pet.repository.PetRepository;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserMainPetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final PetRepository petRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistMetaRepository checklistMetaRepository;
    private final ChecklistHistoryRepository checklistHistoryRepository;

    //TODO-jh : Exception 처리

    // [Summary] 해당 유저의 날짜의 모든 할일 정보를 조회해서, List 로 반환해준다(FindChecklistResponseDto)
    @Override
    public FindChecklistResponseDto findChecklist(User user, LocalDate date) {

        // Get Main Pet Info
        Long petId = user.getMainPetId();
        if (petId == null) throw new UserMainPetNotFoundException();

        Pet pet = petRepository.findByPetId(petId).orElseThrow(PetNotFoundException::new);
        if (pet.isDeactivated()) throw new PetDeactivatedException();
        List<Checklist> findChecklist = checklistRepository.findAllByPet(pet);


        // 1. Date 날짜 필터링 : 조회일(date) 이전에 생성된 체크리스트만 조회
        findChecklist = findChecklist.stream()
                .filter(checklist -> checklist.getDate().isBefore(date.plusDays(1)))
                .collect(Collectors.toList());
        //TODO-jh-exception : 날짜범위외 조회 시 exception 발생

        // 2. isRepeated 필터링
        List<FindChecklistResponseDto.ChecklistDetail> checklistDetailList = findChecklist.stream()
                .map(checklist -> {
                    if (checklist.getIsRepeated() == false) {
                        if (checklist.getDate().equals(date)) {
                            return new FindChecklistResponseDto.ChecklistDetail(
                                    checklist.getChecklistId(),
                                    checklist.getTitle(),
                                    checklist.getExplanation(),
                                    checklist.getDate(),
                                    checklist.getIsDone(),
                                    checklist.getIsRepeated()
                            );
                        } else {
                            return null;
                        }
                    } else {
                        ChecklistMeta checklistMeta = checklistMetaRepository.findByChecklist(checklist)
                                .orElseThrow(ChecklistMetaNotFoundException::new);

                        // Validation #1. Start Date
                        if (checklistMeta.getStartedAt() != null && date.isBefore(checklistMeta.getStartedAt())) {
                            // 조회할 날짜가 StartAt 보다 이전일 순 없다 ex. date(2022-07-01), startAt(2022-08-01)
                            return null;
                        }
                        // Validation #2. End Date
                        if (checklistMeta.getEndedAt() != null && date.isAfter(checklistMeta.getEndedAt())) {
                            // 조회할 날짜가 EndAt 이후일 순 없다 ex. date(2022-07-01), endAt (2022-06-01)
                            return null;
                        }

                        // Filtering #1. eventDeleteDate
                        String date_string = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if (checklistMeta.getEventExceptionDate() != null) {
                            List<String> exceptionDateList = Arrays.asList(checklistMeta.getEventExceptionDate().split(","));
                            if (exceptionDateList.contains(date_string)) {
                                return null;
                            }
                        }
                        // Filtering #2. eventExceptionDate
                        if (checklistMeta.getEventDeleteDate() != null) {
                            List<String> deleteDateList = Arrays.asList(checklistMeta.getEventDeleteDate().split(","));
                            if (deleteDateList.contains(date_string)) {
                                return null;
                            }
                        }

                        // Repeat Check #1. Get Repeat
                        String eventPeriod = checklistMeta.getEventPeriod();
                        String [] eventDate = checklistMeta.getEventDate() == null ? null : checklistMeta.getEventDate().split(",");
                        String eventMonth = checklistMeta.getEventMonth();
                        String eventDay = checklistMeta.getEventDay();
                        String eventWeek = checklistMeta.getEventWeek();

                        // Repeat Check #2. Check Repeat
                        if (eventPeriod.equals("daily")) {
                            // 매일 반복 : 무조건 OK
                        } else if (eventPeriod.equals("weekly")) {
                            // 매주 반복 : 요일만 같으면 무조건 OK
                            if (Arrays.stream(eventDate).anyMatch(date.getDayOfWeek().toString()::equals) == false) {
                                return null;
                            }
                        } else if (eventPeriod.equals("monthly") && eventWeek != null) {
                            // 매달 몇주차 무슨요일 반복 : 주차, 요일이 같으면 OK
                            Calendar cal = Calendar.getInstance();
                            cal.set(date.getMonthValue(), date.getMonthValue()-1, date.getDayOfMonth());
                            cal.setMinimalDaysInFirstWeek(1);
                            if (cal.get(Calendar.WEEK_OF_MONTH) != Integer.parseInt(eventWeek)) {
                                return null;
                            }
                            if (Arrays.stream(eventDate).anyMatch(date.getDayOfWeek().toString()::equals) == false) {
                                return null;
                            }
                        } else if (eventPeriod.equals("monthly") && eventDay != null) {
                            // 매달 며칠 반복 : 날짜가 같으면 OK
                            if (date.getDayOfMonth() != Integer.parseInt(eventDay)) {
                                return null;
                            }
                        } else if (eventPeriod.equals("yearly")) {
                            // 매년 반복 : 월,일이 같으면 OK
                            if (date.getMonthValue() != Integer.parseInt(eventMonth)) {
                                return null;
                            }
                            if (date.getDayOfMonth() != Integer.parseInt(eventDay)) {
                                return null;
                            }
                        } else {
                            return null;
                        }

                        boolean isDone = false;
                        ChecklistHistory checklistHistory =
                                checklistHistoryRepository.findByChecklistAndDate(checklist, date).orElse(null);
                        if (checklistHistory != null) {
                            isDone = checklistHistory.getIsDone();
                        }

                        // Return DTO #
                        return new FindChecklistResponseDto.ChecklistDetail(
                                checklist.getChecklistId(),
                                checklist.getTitle(),
                                checklist.getExplanation(),
                                checklist.getDate(),
                                isDone,
                                checklist.getIsRepeated()

                        );
                    }
                }).filter(checklistDetail -> checklistDetail != null)
                .collect(Collectors.toList());

        return new FindChecklistResponseDto(
                date,
                checklistDetailList
        );
    }

    // [Summary] 해당 유저 앞으로 Checklist 을 신규 생성하고(CreateChecklistRequestDto), 생성한 결과를 리턴해준다(CreateChecklistResponseDto) : 중간
    @Override
    @Transactional
    public CreateChecklistResponseDto createChecklist(User user, CreateChecklistRequestDto createChecklistRequestDto) {
        // Get Main Pet Info
        Long petId = user.getMainPetId();
        if (petId == null) throw new UserMainPetNotFoundException();

        Pet pet = petRepository.findByPetId(petId).orElseThrow(PetNotFoundException::new);
        if (pet.isDeactivated()) throw new PetDeactivatedException();

        // 1. Checklist 생성
        Checklist checklistEntity = Checklist.builder()
                .pet(pet)
                .title(createChecklistRequestDto.getTitle() == null ?
                        "제목 없음" : createChecklistRequestDto.getTitle())
                .explanation(createChecklistRequestDto.getExplanation())
                .date(createChecklistRequestDto.getDate())
                .isDone(createChecklistRequestDto.getIsDone())
                .isRepeated(createChecklistRequestDto.getIsRepeated())
                .build();

        Checklist checklist = checklistRepository.save(checklistEntity);
        //TODO-jh-exception : save 에서 exception 이 발생한다면/!

        // 2. (isRepeated 인 경우) ChecklistMeta 도 생성
        if (createChecklistRequestDto.getIsRepeated() == true) {
            ChecklistMeta checklistMetaEntity = ChecklistMeta.builder()
                    .checklist(checklist)
                    .eventPeriod(createChecklistRequestDto.getRepeatDetail().getEventPeriod())
                    .eventDate(createChecklistRequestDto.getRepeatDetail().getEventDate())
                    .eventMonth(createChecklistRequestDto.getRepeatDetail().getEventMonth())
                    .eventDay(createChecklistRequestDto.getRepeatDetail().getEventDay())
                    .eventWeek(createChecklistRequestDto.getRepeatDetail().getEventWeek())
                    .startedAt(createChecklistRequestDto.getRepeatDetail().getStartedAt())
                    .endedAt(createChecklistRequestDto.getRepeatDetail().getEndedAt())
                    .build();

            ChecklistMeta checklistMeta = checklistMetaRepository.save(checklistMetaEntity);
            //TODO-jh-exception : save 에서 exception 이 발생한다면/!
        }

        return new CreateChecklistResponseDto(
                checklist.getChecklistId()
        );
    }

    // [Summary] checklistId 로 스케줄 정보를 조회해서, 조회 결과를 리턴해준다(FindChecklistByChecklistIdResponseDto)
    @Override
    public FindChecklistByChecklistIdResponseDto findChecklistByChecklistId(Long checklistId) {
        // 1. Checklist, ChecklistMeta 조회
        Checklist findChecklist = checklistRepository.findById(checklistId).orElseThrow(ChecklistNotFoundException::new);
        ChecklistMeta findChecklistMeta = checklistMetaRepository.findByChecklist(findChecklist).orElse(null);

        // 2. Response DTO 리턴
        FindChecklistByChecklistIdResponseDto.RepeatDetail repeatDetail = null;
        if (findChecklist.getIsRepeated() == true) {
            repeatDetail = new FindChecklistByChecklistIdResponseDto.RepeatDetail(
                    findChecklistMeta.getEventPeriod(),
                    findChecklistMeta.getEventDate(),
                    findChecklistMeta.getEventMonth(),
                    findChecklistMeta.getEventDay(),
                    findChecklistMeta.getEventWeek(),
                    findChecklistMeta.getStartedAt(),
                    findChecklistMeta.getEndedAt()
            );
        }

        return new FindChecklistByChecklistIdResponseDto(
                findChecklist.getChecklistId(),
                findChecklist.getTitle(),
                findChecklist.getExplanation(),
                findChecklist.getDate(),
                findChecklist.getIsDone(),
                findChecklist.getIsRepeated(),
                repeatDetail
        );
    }

    // [Summary] checklistId 로 스케줄 정보를 수정하고,(modifyChecklistRequestDto), 수정 결과를 리턴해준다(ModifyChecklistResponseDto)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyChecklistResponseDto modifyChecklist(Long checklistId, LocalDate date, String modifyType, ModifyChecklistRequestDto modifyChecklistRequestDto) {

        // Find # 1. Checklist 조회
        Checklist findChecklist = checklistRepository.findById(checklistId).orElseThrow(ChecklistNotFoundException::new);
//        System.out.println("test 1");
        // Find # 2. ChecklistMeta 조회
        ChecklistMeta findChecklistMeta = checklistMetaRepository.findByChecklist(findChecklist).orElse(null);
//        System.out.println("test 2");
        // Find # 3. ChecklistHistory 조회
        List<ChecklistHistory> findChecklistHistoryList = checklistHistoryRepository.findAllByChecklist(findChecklist);
//        System.out.println("test 1");

        Checklist modifyChecklist = new Checklist();
        // Case # 1. Repeat = N
        if (findChecklist.getIsRepeated() == false) {
            findChecklist.modify(modifyChecklistRequestDto);
            modifyChecklist = checklistRepository.save(findChecklist);

            if (modifyChecklistRequestDto.getIsRepeated() == true) {
                ChecklistMeta checklistMeta = ChecklistMeta.builder()
                        .checklist(findChecklist)
                        .eventPeriod(modifyChecklistRequestDto.getRepeatDetail().getEventPeriod())
                        .eventDate(modifyChecklistRequestDto.getRepeatDetail().getEventDate())
                        .eventMonth(modifyChecklistRequestDto.getRepeatDetail().getEventMonth())
                        .eventDay(modifyChecklistRequestDto.getRepeatDetail().getEventDay())
                        .eventWeek(modifyChecklistRequestDto.getRepeatDetail().getEventWeek())
                        .startedAt(modifyChecklistRequestDto.getRepeatDetail().getStartedAt())
                        .endedAt(modifyChecklistRequestDto.getRepeatDetail().getEndedAt())
                        .build();
                checklistMetaRepository.save(checklistMeta);
            }
        } else {
            // Case # 2. Repeat = Y / modifyType = onlyThis
            if (modifyType.equals("onlyThis")) {
                // Change End Date
                findChecklistMeta.addEventExceptionDate(date);
                checklistMetaRepository.save(findChecklistMeta);

                // If exists, Delete ChecklistHistory
                findChecklistHistoryList.stream()
                        .forEach(checklistHistory -> {
                            if (checklistHistory.getDate().equals(date)) {
                                checklistHistoryRepository.delete(checklistHistory);
                            }
                        });

                // Create New Checklist
                Checklist checklist = Checklist.builder()
                        .originChecklistId(findChecklist.getChecklistId())
                        .pet(findChecklist.getPet())
                        .title(modifyChecklistRequestDto.getTitle())
                        .explanation(modifyChecklistRequestDto.getExplanation())
                        .date(modifyChecklistRequestDto.getDate())
                        .isDone(modifyChecklist.getIsDone())
                        .build();
                modifyChecklist = checklistRepository.save(checklist);

            // Case # 3. Repeat = Y / modifyType = afterThis
            } else if (modifyType.equals("afterThis")) { // 수정 후의 ChecklistHistory 는 날라간다
                // Change End Date
                findChecklistMeta.changeEndedAt(date.minusDays(1));
                checklistMetaRepository.save(findChecklistMeta);

                // Delete ChecklistHistory After this
                findChecklistHistoryList.stream()
                        .forEach(checklistHistory -> {
                            if (checklistHistory.getDate().isAfter(date.minusDays(1))) {
                                checklistHistoryRepository.delete(checklistHistory);
                            }
                        });

                // Create New Checklist
                Checklist checklist = Checklist.builder()
                        .pet(findChecklist.getPet())
                        .title(modifyChecklistRequestDto.getTitle())
                        .explanation(modifyChecklistRequestDto.getExplanation())
                        .date(modifyChecklistRequestDto.getDate())
                        .isDone(modifyChecklistRequestDto.getIsDone())
                        .isRepeated(modifyChecklistRequestDto.getIsRepeated())
                        .build();
                modifyChecklist = checklistRepository.save(checklist);

                // Create New ChecklistMeta
                if (modifyChecklistRequestDto.getIsRepeated() == true) {
                    ChecklistMeta checklistMeta = ChecklistMeta.builder()
                            .checklist(checklist)
                            .eventPeriod(modifyChecklistRequestDto.getRepeatDetail().getEventPeriod())
                            .eventDate(modifyChecklistRequestDto.getRepeatDetail().getEventDate())
                            .eventMonth(modifyChecklistRequestDto.getRepeatDetail().getEventMonth())
                            .eventDay(modifyChecklistRequestDto.getRepeatDetail().getEventDay())
                            .eventWeek(modifyChecklistRequestDto.getRepeatDetail().getEventWeek())
                            .startedAt(modifyChecklistRequestDto.getRepeatDetail().getStartedAt())
                            .endedAt(modifyChecklistRequestDto.getRepeatDetail().getEndedAt())
                            .build();
                    checklistMetaRepository.save(checklistMeta);
                }

            // Case # 4. Repeat = Y / modifyType = all
            } else if (modifyType.equals("all")) { // ChecklistHistory 기록은 그대로 유지

                System.out.println((findChecklist.getIsRepeated()) + " " + modifyChecklistRequestDto.getIsRepeated());
                // ChecklistMeta # 1. Repeat NY : 신규 생성
                if (findChecklist.getIsRepeated() == false && modifyChecklistRequestDto.getIsRepeated() == true) {
                    ChecklistMeta checklistMeta = ChecklistMeta.builder()
                            .checklist(findChecklist)
                            .eventPeriod(modifyChecklistRequestDto.getRepeatDetail().getEventPeriod())
                            .eventDate(modifyChecklistRequestDto.getRepeatDetail().getEventDate())
                            .eventMonth(modifyChecklistRequestDto.getRepeatDetail().getEventMonth())
                            .eventDay(modifyChecklistRequestDto.getRepeatDetail().getEventDay())
                            .eventWeek(modifyChecklistRequestDto.getRepeatDetail().getEventWeek())
                            .startedAt(modifyChecklistRequestDto.getRepeatDetail().getStartedAt())
                            .endedAt(modifyChecklistRequestDto.getRepeatDetail().getEndedAt())
                            .build();
                    checklistMetaRepository.save(checklistMeta);
                }
                // ChecklistMeta # 2. Repeat YN : 기존 삭제
                else if (findChecklist.getIsRepeated() == true && modifyChecklistRequestDto.getIsRepeated() == false) {
                    System.out.println("findChecklist id : " + findChecklist.getChecklistId());
                    checklistMetaRepository.deleteByChecklist(findChecklist);

                    checklistHistoryRepository.deleteAllByChecklist(findChecklist);
                }
                // ChecklistMeta # 3. Repeat YY : 기존 수정
                else if (findChecklist.getIsRepeated() == true && modifyChecklistRequestDto.getIsRepeated() == true)  {
                    findChecklistMeta.modify(modifyChecklistRequestDto.getRepeatDetail());
                    checklistMetaRepository.save(findChecklistMeta);

                    checklistHistoryRepository.deleteAllByChecklist(findChecklist);
                }

                // Modify Checklist after ChecklistMeta
                findChecklist.modify(modifyChecklistRequestDto);
                modifyChecklist = checklistRepository.save(findChecklist);
            }

        }

        return new ModifyChecklistResponseDto(
                modifyChecklist.getChecklistId(),
                modifyChecklist.getTitle(),
                modifyChecklist.getExplanation(),
                modifyChecklist.getDate(),
                modifyChecklist.getIsDone(),
                modifyChecklist.getIsRepeated()
        );
    }

    // [Summary] ChecklistId 로 할일 정보를 삭제하고, 삭제 결과를 리턴해준다(DeleteChecklistResponseDto)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteChecklistResponseDto deleteChecklist(Long checklistId, LocalDate date, String deleteType) {

        // Find # 1. Checklist 조회
        Checklist findChecklist = checklistRepository.findById(checklistId).orElseThrow(ChecklistNotFoundException::new);
        // Find # 2. ChecklistMeta 조회
        ChecklistMeta findChecklistMeta = checklistMetaRepository.findByChecklist(findChecklist).orElse(null);
        // Find # 3. ChecklistHistory 조회
        List<ChecklistHistory> findChecklistHistoryList = checklistHistoryRepository.findAllByChecklist(findChecklist);

        // Logic # 1. (isRepeated = False 인 경우)
        if (findChecklist.getIsRepeated() == false) {
            checklistRepository.deleteById(checklistId);

        // Logic # 2. (isRepeated = True 인 경우)
        } else {
            if (deleteType.equals("onlyThis")) {
                findChecklistMeta.addEventDeleteDate(date);
                checklistMetaRepository.save(findChecklistMeta);

            } else if (deleteType.equals("afterThis")) {
                // # 1. Change End Date
                findChecklistMeta.changeEndedAt(date.minusDays(1));
                checklistMetaRepository.save(findChecklistMeta);

                // # 2. Delete that (OriginChecklistId = checklistId) && (After date)
                checklistRepository.findAllByOriginChecklistId(checklistId).stream()
                        .forEach(checklist -> {
                            if (checklist.getDate().isAfter(date.minusDays(1))) {
                                // Delete # 1. Delete Checklist
                                checklistRepository.delete(checklist);

                                // Delete # 2. Delete on Exception Date
                                findChecklistMeta.deleteDateOnExceptionDateList(checklist.getDate());

                                // Delete # 3. Delete ChecklistHistory : N/A
                            }
                        });
                // # 3. Delete ChecklistHistory
                findChecklistHistoryList.stream()
                                .forEach(checklistHistory -> {
                                    if (checklistHistory.getDate().isAfter(date.minusDays(1))) {
                                        checklistHistoryRepository.delete(checklistHistory);
                                    }
                                });

                checklistMetaRepository.save(findChecklistMeta);
            } else if (deleteType.equals("all")) {
                checklistHistoryRepository.deleteAllByChecklist(findChecklist);
                checklistMetaRepository.deleteByChecklist(findChecklist);
                checklistRepository.deleteAllByOriginChecklistId(checklistId);
                checklistRepository.delete(findChecklist);
            }
        }

        return new DeleteChecklistResponseDto(
                checklistId
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyCheckedResponseDto modifyChecked(Long checklistId, LocalDate date) {
        // Find # 1. Checklist 조회
        Checklist findChecklist = checklistRepository.findById(checklistId).orElseThrow(ChecklistNotFoundException::new);
        // Find # 2. ChecklistHistory 조회
        ChecklistHistory findChecklistHistory = checklistHistoryRepository.findByChecklistAndDate(findChecklist, date).orElse(null);



        // Logic # 1. (isRepeated = False 인 경우) isDone 을 반대로 변경
        boolean changedIsDone = false;
        if (findChecklist.getIsRepeated() == false) {
            if (date.equals(findChecklist.getDate())) {
                changedIsDone = findChecklist.changeIsDone();
                checklistRepository.save(findChecklist);
            }

        // Logic # 2. (isRepeated = True 인 경우)
            // ChecklistHistory 에 이미 값이 있으면, isDone 을 False 로 변경
            // ChecklistHistory 에 값이 없으면, 신규 컬럼 생성 (True)
        } else {
            if (findChecklistHistory != null) {
                changedIsDone = findChecklistHistory.changeIsDone();
                checklistHistoryRepository.save(findChecklistHistory);
            } else {
                ChecklistHistory checklistHistory = ChecklistHistory.builder()
                        .checklist(findChecklist)
                        .date(date)
                        .isDone(true)
                        .build();
                checklistHistoryRepository.save(checklistHistory);
                changedIsDone = true;
            }
        }
        return new ModifyCheckedResponseDto(
                checklistId,
                changedIsDone
        );
    }
}
