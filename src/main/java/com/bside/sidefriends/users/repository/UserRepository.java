package com.bside.sidefriends.users.repository;

import com.bside.sidefriends.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * NOTE: UserRepository 메서드 사용 원칙
     * - `isDeletedFalse`는 soft delete되지 않은 레코드만을 조회하는 메서드
     * - 사용자 레코드 존재 여부 확인 시 `isDeletedFalse`가 붙지 않은 위의 두 개 메서드 이용
     * - 실제 서비스에서 사용자 레코드를 조회할 때는 `isDeletedFalse`가 붙은 아래 두 개 메서드 이용
     * IR
     */

    // TODO: findByUsername, findByProviderAndProviderId가 사실상 동일하므로, 둘 중 하나 삭제 예정. IR.
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
