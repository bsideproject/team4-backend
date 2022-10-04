package com.bside.sidefriends.symptom.repository;

import com.bside.sidefriends.symptom.domain.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
}
