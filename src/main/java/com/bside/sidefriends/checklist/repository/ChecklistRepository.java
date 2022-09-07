package com.bside.sidefriends.checklist.repository;

import com.bside.sidefriends.checklist.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByPetId(String petId);

    List<Checklist> findAllByOriginChecklistId(Long originChecklistId);

    void deleteAllByOriginChecklistId(Long checklistId);
}
