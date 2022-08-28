package com.bside.sidefriends.schedule.service;

import com.bside.sidefriends.schedule.domain.Schedule;
import com.bside.sidefriends.schedule.domain.ScheduleMeta;
import com.bside.sidefriends.schedule.error.exception.ScheduleMetaNotFoundException;
import com.bside.sidefriends.schedule.error.exception.ScheduleNotFoundException;
import com.bside.sidefriends.schedule.repository.ScheduleMetaRepository;
import com.bside.sidefriends.schedule.repository.ScheduleRepository;
import com.bside.sidefriends.schedule.service.dto.*;
import com.bside.sidefriends.users.domain.User;
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
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMetaRepository scheduleMetaRepository;

    //TODO-jh : Exception 처리
    //TODO-jh : pet_id로 된 부분은 모두 pet 으로 변경

    // Summary) 해당 유저의 대표펫의 해당 날짜의 모든 일정 정보를 조회해서, List 로 반환해준다(FindScheduleResponseDto)
    // 1. 해당 user 의 대표 petId에 연관된 모든 일정 중, date 로 먼저 필터링 ( startDate ≤ {date} ≤ endDate )
    // 2. isRepeated = True 인 것 대상으로 *반복 일정 조회 로직 수행*
        // 1. eventDeleteDate, eventExceptionDate 를 필터링 한 데이터만 조회해 와야 한다
        // 2. scheduleMeta 정보로 유효 날짜 추출 (시작일 부터 생성 / 단, 무한대까지 가지 않도록 다음 유효 날짜가 date 를 넘는 경우 종료)
        // 3. 유효 날짜 중 date 가 포함되는 경우 Schedule 데이터 추가
        // 이 때, 기존 일정에 LocalTime 값이 있으면, 반복 일정에도 Time 정보를 넣어줘야 한다
    @Override
    public FindScheduleResponseDto findSchedule(User user, LocalDate date) {

        String petId = user.getMainPetId();
        //TODO-jh : after pet entity added
//        Pet pet = user.getPet();
//        if (pet == null) {
//            throw new IllegalStateException("대표펫이 없습니다.")
//        }
//        if (pet.isDeleted()) {
//            throw new IllegalStateException("이미 삭제된 펫 입니다");
//        }

        List<Schedule> findSchedule = scheduleRepository.findAllByPetId(petId);

        System.out.println("size before filter " + findSchedule.size());

        // 1. date 에 해당되는 일정 필터링
        findSchedule = findSchedule.stream()
                .filter(schedule -> date.isAfter(schedule.getStartDate().minusDays(1))
                        && date.isBefore(schedule.getEndDate().plusDays(1)))
                .collect(Collectors.toList());
        System.out.println("size after filter " + findSchedule.size());

        // 2. 반복 일정 여부 확인
        List<FindScheduleResponseDto.ScheduleDetail> scheduleDetailList = findSchedule.stream()
                .map(schedule -> {
                    if (schedule.getIsRepeated()) {
                        ScheduleMeta scheduleMeta = scheduleMetaRepository.findBySchedule(schedule)
                                .orElseThrow(ScheduleMetaNotFoundException::new);

                        // Validation #1. Start Date
                        if (scheduleMeta.getStartedAt() != null && date.isBefore(scheduleMeta.getStartedAt())) {
                            return null;
                        }
                        // Validation #2. End Date
                        if (scheduleMeta.getEndedAt() != null && date.isAfter(scheduleMeta.getEndedAt())) {
                            return null;
                        }

                        // 1. eventDeleteDate, eventExceptionDate 를 필터링 한 데이터만 조회해 와야 한다
                        String date_string = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if (scheduleMeta.getEventExceptionDate() != null) {
                            List<String> exceptionDateList = Arrays.asList(scheduleMeta.getEventExceptionDate().split(","));
                            if (exceptionDateList.contains(date_string)) {
                                return null;
                            }
                        }
                        if (scheduleMeta.getEventDeleteDate() != null) {
                            List<String> deleteDateList = Arrays.asList(scheduleMeta.getEventDeleteDate().split(","));
                            if (deleteDateList.contains(date_string)) {
                                return null;
                            }
                        }

                        // 2. scheduleMeta 정보로 유효 날짜 추출 (시작일 부터 생성 / 단, 무한대까지 가지 않도록 다음 유효 날짜가 date 를 넘는 경우 종료)
                        // 3. 유효 날짜 중 date 가 포함되는 경우 Schedule 데이터 추가
                        // 이 때, 기존 일정에 LocalTime 값이 있으면, 반복 일정에도 Time 정보를 넣어줘야 한다
                        //TODO-exception : scheduleMeta 의 startDate 는 필수로 들어오도록 해야 한다
                        //TODO-think : schedule 의 startDate 는 scheduleMeta 의 startedAt과 동일하다고 가정하고 진행
                        LocalDate startDate = schedule.getStartDate();

                        String eventPeriod = scheduleMeta.getEventPeriod();
                        String [] eventDate = scheduleMeta.getEventDate() == null ? null : scheduleMeta.getEventDate().split(",");
                        String eventMonth = scheduleMeta.getEventMonth();
                        String eventDay = scheduleMeta.getEventDay();
                        String eventWeek = scheduleMeta.getEventWeek();
                        LocalDate startedAt = scheduleMeta.getStartedAt();
                        LocalDate endedAt = scheduleMeta.getEndedAt() == null ? LocalDate.MAX : scheduleMeta.getEndedAt();

                        if (eventPeriod.equals("daily")) {
                            // 무조건 OK
                        } else if (eventPeriod.equals("weekly")) {
                            // 요일만 같으면 무조건 OK
                            if (Arrays.stream(eventDate).anyMatch(date.getDayOfWeek().toString()::equals) == false) {
                                return null;
                            }
                        } else if (eventPeriod.equals("monthly") && scheduleMeta.getEventWeek() != null) {
                            // 주차, 요일이 같으면 OK
                            Calendar cal = Calendar.getInstance();
                            cal.set(date.getMonthValue(), date.getMonthValue()-1, date.getDayOfMonth());
                            cal.setMinimalDaysInFirstWeek(1);
                            if (cal.get(Calendar.WEEK_OF_MONTH) != Integer.parseInt(eventWeek)) {
                                return null;
                            }
                            if (Arrays.stream(eventDate).anyMatch(date.getDayOfWeek().toString()::equals) == false) {
                                return null;
                            }
                        } else if (eventPeriod.equals("monthly") && scheduleMeta.getEventDay() != null) {
                            // 날짜가 같으면 OK
                            if (date.getDayOfMonth() != Integer.parseInt(eventDay)) {
                                return null;
                            }
                        } else if (eventPeriod.equals("yearly")) {
                            // 월,일이 같으면 OK
                            if (date.getMonthValue() != Integer.parseInt(eventMonth)) {
                                return null;
                            }
                            if (date.getDayOfMonth() != Integer.parseInt(eventDay)) {
                                return null;
                            }
                        } else {
                            return null;
                        }

                        return new FindScheduleResponseDto.ScheduleDetail(
                                schedule.getScheduleId(),
                                schedule.getTitle(),
                                schedule.getStartDate(),
                                schedule.getStartTime(),
                                schedule.getEndDate(),
                                schedule.getEndTime(),
                                schedule.getIsAllDay()
                        );
                    } else {
                        return new FindScheduleResponseDto.ScheduleDetail(
                                schedule.getScheduleId(),
                                schedule.getTitle(),
                                schedule.getStartDate(),
                                schedule.getStartTime(),
                                schedule.getEndDate(),
                                schedule.getEndTime(),
                                schedule.getIsAllDay()
                        );
                    }
                }).filter(scheduleDetail -> scheduleDetail != null)
                .collect(Collectors.toList());

        return new FindScheduleResponseDto(
                date,
                scheduleDetailList
        );
    }

    // Summary) 해당 유저의 대표펫 앞으로 Schedule 을 신규 생성하고(CreateScheduleRequestDto), 생성한 결과를 리턴해준다(CreateScheduleResponseDto)
    @Override
    @Transactional
    public CreateScheduleResponseDto createSchedule(User user, CreateScheduleRequestDto createScheduleRequestDto) {

        String petId = user.getMainPetId();
        //TODO-jh : after pet entity added
//        Pet pet = user.getPet();
//        if (pet == null) {
//            throw new IllegalStateException("대표펫이 없습니다.")
//        }
//        if (pet.isDeleted()) {
//            throw new IllegalStateException("이미 삭제된 펫 입니다");
//        }

        Schedule scheduleEntity = Schedule.builder()
                .petId(petId)
                .title(createScheduleRequestDto.getTitle() == null ?
                        "제목 없음" : createScheduleRequestDto.getTitle())
                .explanation(createScheduleRequestDto.getExplanation())
                .startDate(createScheduleRequestDto.getStartDate())
                .startTime(createScheduleRequestDto.getStartTime())
                .endDate(createScheduleRequestDto.getEndDate() == null ?
                        LocalDate.of(9999,12,31) : createScheduleRequestDto.getEndDate())
                .endTime(createScheduleRequestDto.getEndTime())
                .isAllDay(createScheduleRequestDto.getIsAllDay())
                .isRepeated(createScheduleRequestDto.getIsRepeated())
                .build();

        // 1. Schedule 저장
        Schedule schedule = scheduleRepository.save(scheduleEntity);
        //TODO-jh-exception : save 에서 exception 이 발생한다면/!

        // Schedule 추가 저장 : origin_schedule_id 데이터 저장
        schedule.setOriginScheduleId(schedule.getScheduleId());
        schedule = scheduleRepository.save(scheduleEntity);

        // 2. Schedule 반복 정보 저장
        if (createScheduleRequestDto.getIsRepeated()) {
            ScheduleMeta scheduleMetaEntity = ScheduleMeta.builder()
                    .schedule(schedule)
                    .eventPeriod(createScheduleRequestDto.getRepeatDetail().getEventPeriod())
                    .eventDate(createScheduleRequestDto.getRepeatDetail().getEventDate())
                    .eventMonth(createScheduleRequestDto.getRepeatDetail().getEventMonth())
                    .eventDay(createScheduleRequestDto.getRepeatDetail().getEventDay())
                    .eventWeek(createScheduleRequestDto.getRepeatDetail().getEventWeek())
                    .startedAt(createScheduleRequestDto.getRepeatDetail().getStartedAt())
                    .endedAt(createScheduleRequestDto.getRepeatDetail().getEndedAt())
                    .build();
            ScheduleMeta scheduleMeta = scheduleMetaRepository.save(scheduleMetaEntity);
            //TODO-jh-exception : save 에서 exception 이 발생한다면/!
        }

        return new CreateScheduleResponseDto(
                schedule.getScheduleId()
        );
    }

    // Summary) ScheduleId로 스케줄 정보를 조회해서, 조회 결과를 리턴해준다(findScheduleByScheduleIdResponseDto)
    // 1. schedule 과 그에 연관된 scheduleMeta 를 함께 조회해서, DTO 로 리턴
    @Override
    public FindScheduleByScheduleIdResponseDto findScheduleByScheduleId(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new);
        ScheduleMeta findScheduleMeta = scheduleMetaRepository.findBySchedule(findSchedule).orElse(null);

        FindScheduleByScheduleIdResponseDto.RepeatDetail repeatDetail = null;
        if (findScheduleMeta != null) {
            repeatDetail = new FindScheduleByScheduleIdResponseDto.RepeatDetail(
                    findScheduleMeta.getEventPeriod(),
                    findScheduleMeta.getEventDate(),
                    findScheduleMeta.getEventMonth(),
                    findScheduleMeta.getEventDay(),
                    findScheduleMeta.getEventWeek(),
                    findScheduleMeta.getStartedAt(),
                    findScheduleMeta.getEndedAt()
            );
        }

        return new FindScheduleByScheduleIdResponseDto(
                findSchedule.getScheduleId(),
                findSchedule.getTitle(),
                findSchedule.getExplanation(),
                findSchedule.getStartDate(),
                findSchedule.getStartTime(),
                findSchedule.getEndDate(),
                findSchedule.getEndTime(),
                findSchedule.getIsAllDay(),
                findSchedule.getIsRepeated(),
                repeatDetail
        );
    }

    // {scheduleId로 조회한 Schedule 의 isRepeated}
    // 1. isRepeated = False : 해당 scheduleId 컬럼 수정
    // 2. isRepeated = True
        // 1. modifyType = 이 일정만 수정 : 해당 scheduleId의 ScheduleMeta 의 eventExceptionDate 에 해당 날짜 추가하고, originScheduleId를 현재의 scheduleId로 가지는 신규 컬럼 생성
        // 2. modifyType = 이후 일정 모두 수정 : 해당 scheduleId의 종료일을 date-1로 설정, originScheduleId를 현재의 scheduleId로 가지는 신규 schedule & scheduleMeta 생성
        // 3. modifyType = 전체 일정 모두 수정 : 해당 scheduleId의 컬럼 수정 (exception 들은 수정되지 않는다)
        // Summary) ScheduleId로 스케줄 정보를 수정하고,(modifySchedule), 수정 결과를 리턴해준다(modifyScheduleResponseDto)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyScheduleResponseDto modifySchedule(Long scheduleId, LocalDate date, String modifyType, ModifyScheduleRequestDto modifyScheduleRequestDto) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        Schedule schedule = new Schedule();
        if (findSchedule.getIsRepeated() == false) {
            findSchedule.modify(modifyScheduleRequestDto);

            schedule = scheduleRepository.save(findSchedule);

            if (modifyScheduleRequestDto.getIsRepeated() == true) {
                ScheduleMeta scheduleMetaEntity = ScheduleMeta.builder()
                        .schedule(schedule)
                        .eventPeriod(modifyScheduleRequestDto.getRepeatDetail().getEventPeriod())
                        .eventDate(modifyScheduleRequestDto.getRepeatDetail().getEventDate())
                        .eventMonth(modifyScheduleRequestDto.getRepeatDetail().getEventMonth())
                        .eventDay(modifyScheduleRequestDto.getRepeatDetail().getEventDay())
                        .eventWeek(modifyScheduleRequestDto.getRepeatDetail().getEventWeek())
                        .startedAt(modifyScheduleRequestDto.getRepeatDetail().getStartedAt())
                        .endedAt(modifyScheduleRequestDto.getRepeatDetail().getEndedAt())
                        .build();
                scheduleMetaRepository.save(scheduleMetaEntity);
            }

        } else {
            ScheduleMeta findScheduleMeta = scheduleMetaRepository.findBySchedule(findSchedule)
                    .orElseThrow(ScheduleMetaNotFoundException::new);

            if (modifyType.equals("onlyThis")) {
                // Schedule Meta 의 exception date 에 date 추가
                findScheduleMeta.addEventExceptionDate(date);
                scheduleMetaRepository.save(findScheduleMeta);

                // 신규 컬럼 생성
                Schedule scheduleEntity = Schedule.builder()
                        .originScheduleId(findSchedule.getScheduleId())
                        .petId(findSchedule.getPetId())
                        .title(modifyScheduleRequestDto.getTitle())
                        .explanation(modifyScheduleRequestDto.getExplanation())
                        .startDate(modifyScheduleRequestDto.getStartDate())
                        .startTime(modifyScheduleRequestDto.getStartTime())
                        .endDate(modifyScheduleRequestDto.getStartDate())
                        .endTime(modifyScheduleRequestDto.getEndTime())
                        .isAllDay(modifyScheduleRequestDto.getIsAllDay())
                        .isRepeated(false)
                        .build();
                schedule = scheduleRepository.save(scheduleEntity);

            } else if (modifyType.equals("afterThis")) {
                // 기존 Schedule 의 EndDate 변경
                findSchedule.changeEndDate(date.minusDays(1));
                scheduleRepository.save(findSchedule);

                // 신규 Schedule 생성
                Schedule scheduleEntity = Schedule.builder()
                        .originScheduleId(findSchedule.getScheduleId())
                        .petId(findSchedule.getPetId())
                        .title(modifyScheduleRequestDto.getTitle())
                        .explanation(modifyScheduleRequestDto.getExplanation())
                        .startDate(modifyScheduleRequestDto.getStartDate())
                        .startTime(modifyScheduleRequestDto.getStartTime())
                        .endDate(modifyScheduleRequestDto.getEndDate() == null ?
                                LocalDate.of(9999,12,31) : modifyScheduleRequestDto.getEndDate())
                        .endTime(modifyScheduleRequestDto.getEndTime())
                        .isAllDay(modifyScheduleRequestDto.getIsAllDay())
                        .isRepeated(modifyScheduleRequestDto.getIsRepeated())
                        .build();

                schedule = scheduleRepository.save(scheduleEntity);

                // 신규 Schedule Meta 생성
                if (modifyScheduleRequestDto.getIsRepeated()) {
                    ScheduleMeta scheduleMetaEntity = ScheduleMeta.builder()
                            .schedule(schedule)
                            .eventPeriod(modifyScheduleRequestDto.getRepeatDetail().getEventPeriod())
                            .eventDate(modifyScheduleRequestDto.getRepeatDetail().getEventDate())
                            .eventMonth(modifyScheduleRequestDto.getRepeatDetail().getEventMonth())
                            .eventDay(modifyScheduleRequestDto.getRepeatDetail().getEventDay())
                            .eventWeek(modifyScheduleRequestDto.getRepeatDetail().getEventWeek())
                            .startedAt(modifyScheduleRequestDto.getRepeatDetail().getStartedAt())
                            .endedAt(modifyScheduleRequestDto.getRepeatDetail().getEndedAt())
                            .build();
                    scheduleMetaRepository.save(scheduleMetaEntity);
                }

            } else if (modifyType.equals("all")) {
                // Schedule Meta 먼저 수정
                if (findSchedule.getIsRepeated() == false && modifyScheduleRequestDto.getIsRepeated() == true) { // 신규 생성
                    ScheduleMeta scheduleMetaEntity = ScheduleMeta.builder()
                            .schedule(schedule)
                            .eventPeriod(modifyScheduleRequestDto.getRepeatDetail().getEventPeriod())
                            .eventDate(modifyScheduleRequestDto.getRepeatDetail().getEventDate())
                            .eventMonth(modifyScheduleRequestDto.getRepeatDetail().getEventMonth())
                            .eventDay(modifyScheduleRequestDto.getRepeatDetail().getEventDay())
                            .eventWeek(modifyScheduleRequestDto.getRepeatDetail().getEventWeek())
                            .startedAt(modifyScheduleRequestDto.getRepeatDetail().getStartedAt())
                            .endedAt(modifyScheduleRequestDto.getRepeatDetail().getEndedAt())
                            .build();
                    scheduleMetaRepository.save(scheduleMetaEntity);
                } else if (findSchedule.getIsRepeated() == true && modifyScheduleRequestDto.getIsRepeated() == false) { // 기존 삭제
                    System.out.println("hi !");
                    scheduleMetaRepository.deleteBySchedule(findSchedule);
                } else if (findSchedule.getIsRepeated() == true && modifyScheduleRequestDto.getIsRepeated() == true ) { // 기존 수정
                    findScheduleMeta.modify(modifyScheduleRequestDto.getRepeatDetail());
                    scheduleMetaRepository.save(findScheduleMeta);
                }

                // Schedule 수정
                findSchedule.modify(modifyScheduleRequestDto);
                schedule = scheduleRepository.save(findSchedule);
            }
        }
        return new ModifyScheduleResponseDto(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getExplanation(),
                schedule.getStartDate(),
                schedule.getStartTime(),
                schedule.getEndDate(),
                schedule.getEndTime(),
                schedule.getIsAllDay(),
                schedule.getIsRepeated()
        );
    }

    // Summary) ScheduleId로 스케줄 정보를 삭제하고, 삭제 결과를 리턴해준다(deleteScheduleResponseDto)
    // {scheduleId로 조회한 Schedule 의 isRepeated}
    // 1. isRepeated = False : 해당 scheduleId 컬럼 삭제
    // 2. isRepeated = True
        // 1. deleteType = 이 일정만 삭제 : 해당 scheduleId의 ScheduleMeta 의 eventDeleteDate 에 해당 날짜 추가
        // 2. deleteType = 이후 일정 모두 삭제 : 해당 scheduleId의 endDate 를 date-1 로 설정 / (만약 예외 데이터가 있다면) 각 exception 데이터 별로 날짜가 date 이후인 것도 모두 삭제해야 한다
        // 3. deleteType = 전체 일정 모두 삭제 : originScheduleId가 scheduleId인 모든 값을 삭제해야 한다
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteScheduleResponseDto deleteSchedule(Long scheduleId, LocalDate date, String deleteType) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        if (findSchedule.getIsRepeated() == false) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            ScheduleMeta scheduleMeta = scheduleMetaRepository.findBySchedule(findSchedule)
                    .orElseThrow(ScheduleMetaNotFoundException::new);
            if (deleteType.equals("onlyThis")) {
                scheduleMeta.addEventDeleteDate(date);

                scheduleMetaRepository.save(scheduleMeta);
            } else if (deleteType.equals("afterThis")) {
                findSchedule.changeEndDate(date.minusDays(1));

                scheduleRepository.save(findSchedule);

                // 전체 삭제하돼, 원본 데이터만 삭제하면 안된다
                scheduleRepository.deleteAllByOriginScheduleIdAndScheduleIdNot(scheduleId, scheduleId);
            } else if (deleteType.equals("all")) {
                scheduleMetaRepository.deleteBySchedule(findSchedule);
                scheduleRepository.deleteAllByOriginScheduleId(scheduleId);
            }
        }

        return new DeleteScheduleResponseDto(
                scheduleId
        );
    }
    // Delete = Hard Delete : soft delete 가 아닌 hard delete 사용 (삭제한 스케줄 정보는 필요없는 정보라고 판단)

}
