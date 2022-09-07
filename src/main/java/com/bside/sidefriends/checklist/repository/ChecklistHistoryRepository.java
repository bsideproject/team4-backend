package com.bside.sidefriends.checklist.repository;

import com.bside.sidefriends.checklist.domain.Checklist;
import com.bside.sidefriends.checklist.domain.ChecklistHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistHistoryRepository extends JpaRepository<ChecklistHistory, Long> {

    Optional<ChecklistHistory> findByChecklistAndDate(Checklist checklist, LocalDate date);

    Optional<ChecklistHistory> findByChecklist(Checklist findChecklist);

    void deleteAllByChecklist(Checklist findChecklist);

    List<ChecklistHistory> findAllByChecklist(Checklist findChecklist);

    List<ChecklistHistory> findByDate(LocalDate date);
}
