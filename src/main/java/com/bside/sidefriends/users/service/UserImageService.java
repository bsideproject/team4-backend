package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.UserImage;
import com.bside.sidefriends.users.service.dto.GetUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.UploadUserImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {
    
    /**
     * 유저 이미지 업로드
     * - 유저 이미지 최초 저장
     * - 유저 이미지 변경
     * @param userId 이미지를 업로드할 회원 id
     * @param file 회원 이미지 파일
     * @return {@link UploadUserImageResponseDto} 회원 이미지 업로드 응답 DTO
     */
    UploadUserImageResponseDto uploadUserImage(Long userId, MultipartFile file);

    /**
     * 유저 이미지 반환
     * @param userId 이미지(경로)를 반환할 회원 username
     * @return {@link GetUserImageResponseDto} 회원 이미지 반환 응답 DTO
     */
    GetUserImageResponseDto getUserImage(Long userId);

//    /**
//     * 유저 이미지 삭제: 필요할 때 구현 예정
//     * @param userId
//     * @return
//     */
//    DeleteUserImageResponseDto deleteUserImage(Long userId);

}
