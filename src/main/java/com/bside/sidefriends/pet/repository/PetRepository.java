package com.bside.sidefriends.pet.repository;

import com.bside.sidefriends.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    public Optional<Pet> findByPetId(Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false")
    public Optional<Pet> findByPetIdAndIsDeletedFalse(@Param("petId") Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false"
            + " AND p.isDeactivated = false")
    public Optional<Pet> findByPetIdAndIsDeletedFalseAndIsDeactivatedFalse(
            @Param("petId") Long petId);

    @Query("SELECT p FROM Pet p"
            + " WHERE p.petId = :petId"
            + " AND p.isDeleted = false"
            + " AND p.isDeactivated = true")
    public Optional<Pet> findByPetIdAndIsDeletedFalseAndIsDeactivatedTrue(
            @Param("petId") Long petId);



}
