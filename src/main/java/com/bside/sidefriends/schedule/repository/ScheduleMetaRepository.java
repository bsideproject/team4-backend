package com.bside.sidefriends.schedule.repository;

import com.bside.sidefriends.schedule.domain.Schedule;
import com.bside.sidefriends.schedule.domain.ScheduleMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleMetaRepository extends JpaRepository<ScheduleMeta, Long> {

    Optional<ScheduleMeta> findBySchedule(Schedule schedule);

    void deleteBySchedule(Schedule schedule);
}
