package com.bside.sidefriends.checklist.repository;

import com.bside.sidefriends.checklist.domain.Checklist;
import com.bside.sidefriends.checklist.domain.ChecklistMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChecklistMetaRepository extends JpaRepository<ChecklistMeta, Long> {

    Optional<ChecklistMeta> findByChecklist(Checklist checklist);

    void deleteByChecklist(Checklist findChecklist);
}
