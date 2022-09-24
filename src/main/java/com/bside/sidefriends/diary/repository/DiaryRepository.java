package com.bside.sidefriends.diary.repository;

import com.bside.sidefriends.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT * FROM DIARY d" +
            "WHERE d.pet.petId = :petId")
    List<Diary> findAllByPetId(@Param("petId") Long petId);
}
