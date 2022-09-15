package com.bside.sidefriends.schedule.repository;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPet(Pet pet);

    void deleteAllByOriginScheduleId(Long scheduleId);
    void deleteAllByOriginScheduleIdAndScheduleIdNot(Long originScheduleId, Long scheduleId);

    List<Schedule> findAllByOriginScheduleId(Long scheduleId);
}
