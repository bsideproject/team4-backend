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

    /**
     * 회원 이름
     * - Oauth 로그인 시 SNS 계정 이름으로 기본값 설정
     * - 추후 변경 가능
     */
    @Column(nullable = false)
    String name;

    /**
     * 회원 가족 별칭
     */
    String nickname;

    /**
     * 회원 이메일
     * - 소셜 로그인 시 SNS 계정 이메일로 설정
     */
    @Email
    @Column(nullable = false)
    String email;

    /**
     * 회원별 대표펫 id
     */
    String mainPetId;

    // 회원 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    // 스프링 시큐리티 사용자 정보
    String username;

    // oauth 인증 제공자
    String provider;

    // oauth 인증 제공 서버에서의 사용자 id
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
    public User(String name, String nickname, String email, Role role,
                String provider, String providerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.nickname = nickname;
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
