package com.bside.sidefriends.quick.repository;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.quick.domain.Quick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuickRepository extends JpaRepository<Quick, Long> {
    List<Quick> findAllByPet(Pet pet);
}
