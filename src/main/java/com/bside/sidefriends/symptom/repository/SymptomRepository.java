package com.bside.sidefriends.symptom.repository;

import com.bside.sidefriends.symptom.domain.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Optional<Symptom> findByPetPetIdAndDate(Long PetId, LocalDate date);
}
