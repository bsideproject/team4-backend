package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.UserImage;
import com.bside.sidefriends.users.service.dto.DeleteUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.GetUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.UploadUserImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {

    /**
     * 회원가입 시, 프로필 정보 저장
     * TODO: 삭제 필요
     */
    boolean createUserImage(UserImage userImage);

    /**
     * 유저 이미지 업로드
     * - 유저 이미지 최초 저장
     * - 유저 이미지 변경
     * @param
     * @return
     */
    // TODO: request dto 없이 바로 파일을 넘겨도 괜찮은가
    UploadUserImageResponseDto uploadUserImage(Long userId, MultipartFile file);

    /**
     * 유저 이미지 반환
     * @param userId
     * @return
     */
    GetUserImageResponseDto getUserImage(Long userId);

    /**
     * 유저 이미지 삭제
     * @param userId
     * @return
     */
    DeleteUserImageResponseDto deleteUserImage(Long userId);

}
