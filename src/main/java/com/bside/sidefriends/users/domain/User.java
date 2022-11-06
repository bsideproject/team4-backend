package com.bside.sidefriends.users.domain;

import com.bside.sidefriends.diary.domain.Diary;
import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.users.service.dto.ModifyUserRequestDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 회원 이름
    @Column(nullable = false)
    private String name;

    // 회원 이메일
    @Email
    @Setter
    @Column(nullable = false)
    private String email;

    // 대표펫 id
    private Long mainPetId;

    // 회원 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 스프링 시큐리티 사용자 정보
    private String username;

    // oauth 인증 제공자
    private String provider;

    // oauth 인증 제공 서버에서의 사용자 id
    private String providerId;

    // 사용자 계정 생성 시각
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // 사용자 계정 업데이트 시각
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 사용자 계정 삭제 여부
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

    // 사용자 가족
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    // 사용자 이미지
    @OneToOne(mappedBy = "user")
    private UserImage userImage;

    // 사용자 펫
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final List<Pet> pets = new ArrayList<>();

    // 사용자 펫 한줄일기
    @OneToOne(mappedBy = "user")
    private Diary diary;

    // 가족 그룹 가입
    public void setFamily(Family family) {
        this.family = family;
    }

    // 가족 그룹 해제
    public void leaveFamily() {
        this.role = Role.ROLE_USER;
        this.setFamily(null);
    }

    // 사용자 정보 수정
    public void modify(ModifyUserRequestDto modifyUserRequestDto) {
        this.name = modifyUserRequestDto.getName();
    }

    // 사용자 계정 삭제
    public void delete() {
        this.isDeleted = true;
        this.leaveFamily();
    }

    // 사용자 계정 복원
    public void restore() {
        this.isDeleted = false;
        this.role = Role.ROLE_USER;
    }

    // 사용자 역할 변경
    public void changeRole(Role role) {
        this.role = role;
    }

    // 사용자 가족 정보 반환
    public Long getFamilyIdInfo() {
        return this.family == null ? null : this.family.getFamilyId();
    }

    // 사용자 이미지 반환
    public String getImageUrlInfo() {
        return this.userImage == null ? null : this.userImage.getImageUrl();
    }

    // 사용자 펫 등록
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setUser(this);
    }

    // 대표펫 등록
    public void setMainPet(Long petId) {
        this.mainPetId = petId;
    }


    /** 사용자 권한
     * ROLE_USER: 일반 가족 구성원
     * ROLE_MANAGER: 가족 그룹장
     */
    public enum Role {
        ROLE_USER, ROLE_MANAGER;
    }

}
