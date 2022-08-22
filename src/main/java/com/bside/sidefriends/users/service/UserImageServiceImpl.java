package com.bside.sidefriends.users.service;

import com.bside.sidefriends.common.util.ImageHandler;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.domain.UserImage;
import com.bside.sidefriends.users.error.exception.UserNotFoundException;
import com.bside.sidefriends.users.repository.UserImageRepository;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.dto.DeleteUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.GetUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.UploadUserImageResponseDto;
import com.bside.sidefriends.users.service.util.LocalImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserImageServiceImpl implements UserImageService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final ImageHandler imageHandler;


    // TODO: 이미지 업로드 로직 리팩토링
    @Override
    @Transactional
    public UploadUserImageResponseDto uploadUserImage(Long userId, MultipartFile file) {

        User findUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalStateException("이미지를 업로드할 회원이 존재하지 않습니다.")
        );

        if (findUser.isDeleted()) {
            throw new IllegalStateException("이미지를 업로드할 회원이 이미 삭제된 회원입니다.");
        }

        Optional<UserImage> findUserImage = userImageRepository.findByUser(findUser);
        UserImage userImageEntity;

        // 이미지 저장
        String imageName = file.getOriginalFilename();
        String imagePath;

        if (findUserImage.isPresent()) {
            userImageEntity = findUserImage.get();

            try {
                imagePath = imageHandler.saveImage(file);
            } catch (IOException e) {
                throw new IllegalStateException("회원 이미지 저장 중 오류가 발생했습니다.");
            }

            // 변경 감지 업데이트
            userImageEntity.setImageName(imageName);
            userImageEntity.setImageId(imagePath); // TODO: 이미지 id는 일단 path로 저장한 상태
            userImageEntity.setImageUrl("http://localhost:8080"); // TODO: 이미지 url 삭제?
        } else {

            try {
                imagePath = imageHandler.saveImage(file);
            } catch (IOException e) {
                throw new IllegalStateException("회원 이미지 저장 중 오류가 발생했습니다.");
            }

            userImageEntity = UserImage.builder()
                    .user(findUser)
                    .imageName(imageName)
                    .imageId(imagePath) // TODO: 이미지 id 일단 path로 저장한 상태
                    .imageUrl("http://localhost:8080/") // TODO: 삭제 필요
                    .build();
        }

        userImageRepository.save(userImageEntity);

        return new UploadUserImageResponseDto(
                findUser.getUserId(),
                userImageEntity.getImageId()
        );
    }

    @Override
    public GetUserImageResponseDto getUserImage(Long userId) {
        return null;
    }

    @Override
    public DeleteUserImageResponseDto deleteUserImage(Long userId) {
        return null;
    }

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
