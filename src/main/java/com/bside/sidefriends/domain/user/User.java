package com.bside.sidefriends.domain.user;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    // 회원 정보
    @Column(nullable = false)
    String username;

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
    @CreationTimestamp
    LocalDateTime createdAt;

    // 사용자 계정 업데이트 시각
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public enum Role {
        /**
         * ROLE_USER: 일반 가족 구성원
         * ROLE_MANAGER: 가족 그룹장
         */
        ROLE_USER, ROLE_MANAGER;
    }

}
