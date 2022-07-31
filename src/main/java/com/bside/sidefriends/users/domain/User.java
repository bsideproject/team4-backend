package com.bside.sidefriends.users.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    Long id;

    // 회원 이름
    @Column(nullable = false)
    String name;

    // 회원 닉네임
    String nickname;

    /**
     * 스프링 시큐리티 세션에서의 회원 관리를 위한 username
     * - oauth 로그인 시 provider_providerId 형태
     */
    String username;

    // 회원 전화번호
    String phoneNumber;

    /**
     * 회원 이메일
     * - 소셜 로그인 기반 회원가입의 경우 oauth 인증 정보에서 얻어 오게 됨
     */
    @Email
    @Column(nullable = false)
    String email;

    // 회원 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    // oauth 로그인 provider
    String provider;

    // oauth 로그인 정보 id
    String providerId;

    // 사용자 계정 생성 시각
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    // 사용자 계정 업데이트 시각
    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public User(String name, String nickname, String phoneNumber, String email, Role role,
                String provider, String providerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum Role {
        /**
         * ROLE_USER: 일반 가족 구성원
         * ROLE_MANAGER: 가족 그룹장
         */
        ROLE_USER, ROLE_MANAGER;
    }


}
