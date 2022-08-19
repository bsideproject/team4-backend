package com.bside.sidefriends.quick.repository;

import com.bside.sidefriends.quick.domain.Quick;
import com.bside.sidefriends.quick.domain.QuickHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface QuickHistoryRepository extends JpaRepository<QuickHistory, Long> {

    Optional<QuickHistory> findByQuickAndCreatedAt(Quick quick, LocalDate createdAt);

    Optional<QuickHistory> findByQuick(Quick quick);
}
