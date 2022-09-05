package com.bside.sidefriends.pet.domain;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.pet.service.dto.ModifyPetRequestDto;
import com.bside.sidefriends.users.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Pet {

    // 반려동물 id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    /**
     * NOTE: 반려동물 및 타 엔티티와의 관계 설정
     * - 공유 펫의 경우 가족과 가족(1):펫(n) 관계
     * - 개인 펫의 경우 사용자와 사용자(1):펫(n) 관계
     * - 추후 로직 구현에 문제 있을 시, 사용자와 펫 간 릴레이션 엔티티로 풀어낼 수도 있음
     * IR
     */

    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 가족
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    // 공유 타입
    // TODO: 공유 타입 속성 필요성 검토
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetShareScope shareScope;

    // 이름
    @Column(nullable = false)
    @Setter
    private String name;

    // 품종
    @Setter
    private String breed;

    // 성별
    @Enumerated(EnumType.STRING)
    @Setter
    private PetGender gender;

    // 생일
    @Setter
    private String birthday;

    // 입양일
    @Setter
    private String adoptionDate;

    // 나이
    @Setter
    private Long age;

    // 동물등록번호
    @Setter
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

    // TODO: 펫 이미지

    // 질환
    // TODO: 질환 enum 관리 여부

    // 중성화 여부
    // TODO: 중성화 필요 여부 및 불필요 시 성별 기록 여부

    // 혈액형
    // TODO: 혈액형 기록 필요 여부 및 기록 시 enum 관리 여부

    // 펫 기록 활성화
    public void activate() {
        this.isDeactivated = false;
    }
    // 펫 기록 비활성화
    public void deactivate() {
        this.isDeactivated = true;
    }

    // 펫 삭제
    public void delete() {
        this.isDeleted = true;
    }

}