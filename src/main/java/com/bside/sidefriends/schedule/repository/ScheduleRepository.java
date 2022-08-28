package com.bside.sidefriends.schedule.repository;

import com.bside.sidefriends.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPetId(String petId);
    //TODO-jh : pet
    // List<Schedule> findByPet(Pet pet); 로 변경

    void deleteAllByOriginScheduleId(Long scheduleId);
    void deleteAllByOriginScheduleIdAndScheduleIdNot(Long originScheduleId, Long scheduleId);
}
