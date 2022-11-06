package com.bside.sidefriends.diary.repository;

import com.bside.sidefriends.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d" +
            " WHERE d.pet.petId = :petId")
    List<Diary> findAllByPetId(@Param("petId") Long petId);

    @Query("SELECT d FROM Diary d" +
            " WHERE d.pet.petId = :petId" +
            " AND d.pet.isDeactivated = false")
    List<Diary> findAllByPetIdAndPetIsDeactivatedFalse(@Param("petId") Long petId);

    @Query("SELECT d FROM Diary d" +
            " WHERE d.pet.petId = :petId" +
            " AND d.createdAt >= :startDateTime" +
            " AND d.createdAt < :endDateTime")
    List<Diary> findAllByPetIdAndDate(@Param("petId") Long petId,
                                      @Param("startDateTime") LocalDateTime startDateTime,
                                      @Param("endDateTime") LocalDateTime endDateTime);

    boolean existsByUserUsername(String username);
}
