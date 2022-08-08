package com.bside.sidefriends.users.repository;

import com.bside.sidefriends.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 존재 여부 조회
    boolean existsByUsername(String username);

    // provider, providerId로 유저 조회
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
