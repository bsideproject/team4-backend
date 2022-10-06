package com.bside.sidefriends.pet.domain;

import com.bside.sidefriends.diary.domain.Diary;
import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.symptom.domain.Symptom;
import com.bside.sidefriends.users.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 펫 이미지
    @OneToOne(mappedBy = "pet")
    private PetImage petImage;

    // 펫 한줄일기
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pet")
    private final List<Diary> diaries = new ArrayList<>();

    // 펫 이상징후
    @OneToOne(mappedBy = "pet")
    private Symptom symptom;

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

    // 사용자 펫 설정
    public void setUser(User user) {
        this.user = user;
    }

    // 가족 펫 설정
    public void setFamily(Family family) {
        this.family = family;
        this.shareScope = PetShareScope.FAMILY;
    }

    // 펫 가족 정보 반환
    public Long getFamilyIdInfo() {
        return this.family == null ? null : this.family.getFamilyId();
    }

    // 펫 이미지 url 반환
    public String getImageUrlInfo() {
        return this.petImage == null ? null : this.petImage.getImageUrl();
    }

}
