package com.bside.sidefriends.users.repository;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findByUser(User user);

}
