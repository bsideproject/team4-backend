package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.UserImage;

public interface UserImageService {

    /**
     * 회원가입 시, 프로필 정보 저장
     */
    boolean createUserImage(UserImage userImage);
}
