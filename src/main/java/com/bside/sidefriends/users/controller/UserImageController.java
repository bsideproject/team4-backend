package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.annotation.ValidImage;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.security.auth.LoginUsername;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.service.UserImageService;
import com.bside.sidefriends.users.service.dto.GetUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.UploadUserImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@SideFriendsController
@RequiredArgsConstructor
@Validated
public class UserImageController {

    private final UserImageService userImageService;

    @PostMapping("/users/image")
    public ResponseEntity<ResponseDto<UploadUserImageResponseDto>> uploadUserImage(@LoginUsername String username,
                                                                                  @ValidImage @RequestPart("file") MultipartFile file) {

        UploadUserImageResponseDto uploadUserImageResponseDto = userImageService.uploadUserImage(username, file);
        ResponseDto<UploadUserImageResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_IMAGE_UPLOAD_SUCCESS, uploadUserImageResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    // FIXME: 이미지만 조회하는 API 필요할지 고민
    @GetMapping("/users/image")
    public ResponseEntity<ResponseDto<GetUserImageResponseDto>> getUserImage(@LoginUsername String username) {

        GetUserImageResponseDto getUserImageResponseDto = userImageService.getUserImage(username);
        ResponseDto<GetUserImageResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_IMAGE_DOWNLOAD_SUCCESS, getUserImageResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }
}
