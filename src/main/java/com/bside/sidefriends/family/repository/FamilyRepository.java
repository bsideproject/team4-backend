package com.bside.sidefriends.family.repository;

import com.bside.sidefriends.family.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query("SELECT f from Family f"
            + " WHERE f.familyId = :familyId"
            + " AND f.isDeleted = false")
    Optional<Family> findByFamilyIdAndIsDeletedFalse(@Param("familyId") Long familyId);
}
