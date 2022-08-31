package com.bside.sidefriends.users.repository;

import com.bside.sidefriends.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    @Query("SELECT u from User u"
            + " WHERE u.username = :username"
            + " AND u.isDeleted = false")
    Optional<User> findByUsernameAndIsDeletedFalse(@Param("username") String username);

    @Query("SELECT u from User u"
            + " WHERE u.userId = :userId"
            + " AND u.isDeleted = false")
    Optional<User> findByUseridAndIsDeletedFalse(@Param("userId") Long userId);

}
