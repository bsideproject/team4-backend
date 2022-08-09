package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.UserImage;
import com.bside.sidefriends.users.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;


    @Override
    @Transactional
    public boolean createUserImage(UserImage userImageEntity) {

        UserImage userImage = userImageRepository.save(userImageEntity);

        if (userImage.getUserImageId() != null) {
            return true;
        }
        return false;
    }
}
