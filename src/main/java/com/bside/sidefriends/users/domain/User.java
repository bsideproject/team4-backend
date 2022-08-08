package com.bside.sidefriends.users.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    // 회원 이름
    @Column(nullable = false)
    private String name;

    // 회원 가족 별칭
    private String nickname;

    // 회원 이메일
    @Email
    @Setter
    @Column(nullable = false)
    private String email;

    // 회원별 대표펫 id
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
    @Column(nullable = false)
    private boolean isDeleted;


    @Builder
    public User(String name, String nickname, String email, String mainPetId, String username, Role role, String provider, String providerId,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        this.name = name;
        this.nickname = nickname;
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

    public enum Role {
        /**
         * ROLE_USER: 일반 가족 구성원
         * ROLE_MANAGER: 가족 그룹장
         */
        ROLE_USER, ROLE_MANAGER;

    }

    public void modify(ModifyUserRequestDto modifyUserRequestDto) {
        this.name = modifyUserRequestDto.getName();
    }

    public void delete() {
        this.isDeleted = true;
    }

}
