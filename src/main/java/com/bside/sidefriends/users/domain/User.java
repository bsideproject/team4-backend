package com.bside.sidefriends.users.domain;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.users.service.dto.ModifyUserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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

    // 회원별 대표펫 id
    // TODO: pet 교체. IR.
    private String mainPetId;

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

    @Builder
    public User(String name, String email, String mainPetId, String username, Role role, String provider, String providerId,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        this.name = name;
        this.email = email;
        this.mainPetId = mainPetId;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

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
        if (this.family == null) {
            return null;
        } else {
            return this.family.getFamilyId();
        }
    }

    // 사용자 이미지 반환
    public String getImageUrlInfo() {
        if (this.userImage == null) {
            return null;
        } else {
            return this.userImage.getImageUrl();
        }
    }


    public enum Role {
        /**
         * ROLE_USER: 일반 가족 구성원
         * ROLE_MANAGER: 가족 그룹장
         */
        ROLE_USER, ROLE_MANAGER;

    }

}
