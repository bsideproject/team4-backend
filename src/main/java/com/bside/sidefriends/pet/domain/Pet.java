package com.bside.sidefriends.pet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Pet {

    // 반려동물 id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    // 사용자

    // 공유 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetShareScope shareScope;

    // 이름
    @Column(nullable = false)
    private String name;

    // 품종
    private String breed;

    // 성별
    @Enumerated(EnumType.STRING)
    private PetGender gender;

    // 생일
    private String birthday;

    // 입양일
    private String adoptionDate;

    // 나이
    private Long age;

    // 동물등록번호
    private String animalRegistrationNumber;

    // 기록 중지 여부
    @Column(nullable = false)
    private boolean isDeactivated; // 초깃값 false

    // 삭제 여부
    @Column(nullable = false)
    private boolean isDeleted; // default false

    // 생성 시각
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 시각
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 질환
    // TODO: 질환 enum 관리 여부

    // 중성화 여부
    // TODO: 중성화 필요 여부 및 불필요 시 성별 기록 여부

    // 혈액형
    // TODO: 혈액형 기록 필요 여부 및 기록 시 enum 관리 여부


}
