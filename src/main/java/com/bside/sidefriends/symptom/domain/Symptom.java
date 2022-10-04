package com.bside.sidefriends.symptom.domain;

import com.bside.sidefriends.pet.domain.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Symptom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long symptomId;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private String date;

    private String symptomList;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Symptom(Pet pet, String date, String symptomList) {
        this.pet = pet;
        this.date = date;
        this.symptomList = symptomList;
    }

}
