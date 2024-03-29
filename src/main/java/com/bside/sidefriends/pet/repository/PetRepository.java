package com.bside.sidefriends.pet.repository;

import com.bside.sidefriends.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByPetId(Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false")
    Optional<Pet> findByPetIdAndIsDeletedFalse(@Param("petId") Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false"
            + " AND p.isDeactivated = false")
    Optional<Pet> findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(@Param("petId") Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false"
            + " AND p.isDeactivated = true")
    Optional<Pet> findByPetIdAndIsDeletedFalseAndIsDeactivatedTrue(@Param("petId") Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.user.userId = :userId"
            + " AND p.isDeleted = false"
    )
    List<Pet> findAllByUserIdAndIsDeletedFalse(@Param("userId") Long userId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.family.familyId = :familyId"
            + " AND p.isDeleted = false"
    )
    List<Pet> findAllByFamilyIdAndIsDeletedFalse(@Param("familyId") Long familyId);

    // TODO: family_id, user_id join 쿼리


}
