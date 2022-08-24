package com.bside.sidefriends.users.service.util;

import com.bside.sidefriends.common.util.ImageHandler;
import com.bside.sidefriends.users.service.dto.NginxImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class NginxImageHandler implements ImageHandler {

    private final RestTemplate restTemplate;
    private static final String NGINX_STORAGE_SERVER_URL = "http://localhost:8888";

    @Override
    public String saveImage(MultipartFile multipartFile) throws IOException {

        String uploadImageUrl = NGINX_STORAGE_SERVER_URL + "/images/upload";
        HttpEntity<MultiValueMap<String, Object>> httpEntity = createHttpEntityForImageUpload(multipartFile);

        ResponseEntity<NginxImageResponseDto> response = restTemplate.postForEntity(uploadImageUrl, httpEntity, NginxImageResponseDto.class);

        Objects.requireNonNull(response.getBody(), "응답 데이터가 비어 있습니다.");

        if (response.getStatusCode() != HttpStatus.OK || response.getBody().getCode() != HttpStatus.OK.value()) {
            throw new IllegalStateException("이미지 저장 중 오류가 발생했습니다.");
        }

        return response.getBody().getData().getFileId();
    }

    @Override
    public boolean deleteImage(String imagePath) throws IOException {

        String deleteImageUrl = NGINX_STORAGE_SERVER_URL + "/images/delete" + imagePath;

        ResponseEntity<NginxImageResponseDto> response = restTemplate.postForEntity(deleteImageUrl, HttpEntity.EMPTY, NginxImageResponseDto.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody().getCode() != HttpStatus.OK.value()) {
            throw new IllegalStateException("이미지 삭제에 실패했습니다.");
        }

        return false;
    }


    private HttpEntity<MultiValueMap<String, Object>> createHttpEntityForImageUpload(MultipartFile multipartFile) {

        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 바디
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(); // HttpEntity 안에 들어갈 수 있는 데이터 타입 MultiValueMap
        body.add("file", multipartFile.getResource());

        return new HttpEntity<>(body, headers);

    }


}
