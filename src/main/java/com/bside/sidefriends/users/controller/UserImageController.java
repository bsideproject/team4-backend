package com.bside.sidefriends.users.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.common.annotation.ValidImage;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.users.service.UserImageService;
import com.bside.sidefriends.users.service.dto.GetUserImageResponseDto;
import com.bside.sidefriends.users.service.dto.UploadUserImageResponseDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@SideFriendsController
@RequiredArgsConstructor
@Validated
@ApiResponses({
        @ApiResponse(code=001, message = "입력 값이 올바르지 않습니다."),
        @ApiResponse(code=003, message = "허용되지 않은 요청 방법입니다.")
})
@ApiIgnore
public class UserImageController {

    private final UserImageService userImageService;

    @ApiResponses({
            @ApiResponse(code=180, message = "회원 이미지 업로드에 성공했습니다. (200)")
    })
    @PostMapping("/users/{userId}/image")
    public ResponseEntity<ResponseDto<UploadUserImageResponseDto>> uploadUserImage(@PathVariable("userId") Long userId,
                                                                                  @ValidImage @RequestPart("file") MultipartFile file) {


        UploadUserImageResponseDto uploadUserImageResponseDto = userImageService.uploadUserImage(userId, file);
        ResponseDto<UploadUserImageResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_IMAGE_UPLOAD_SUCCESS, uploadUserImageResponseDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponses({
            @ApiResponse(code=181, message = "회원 이미지 조회에 성공했습니다. (200)")
    })
    @GetMapping("/users/{userId}/image")
    public ResponseEntity<ResponseDto<GetUserImageResponseDto>> getUserImage(@PathVariable("userId") Long userId) {

        GetUserImageResponseDto getUserImageResponseDto = userImageService.getUserImage(userId);
        ResponseDto<GetUserImageResponseDto> responseDto = ResponseDto.onSuccessWithData(
                ResponseCode.U_IMAGE_DOWNLOAD_SUCCESS, getUserImageResponseDto);

        return ResponseEntity.ok().body(responseDto);

    }
}
