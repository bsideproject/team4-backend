package com.bside.sidefriends.users.service.util;

import com.bside.sidefriends.common.util.ImageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class LocalImageHandler implements ImageHandler {

    // TODO: path 정규화(파일 시스템에 따라 경로 다를 수 있음)
    private final String serverPath = System.getProperty("user.dir");

    // TODO: 이미지 서버 설정 주입 방법 변경
    @Value("${image.local.upload-path}")
    private String imageFolder;

    @Override
    public String saveImage(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        if (fileName == null) {
            throw new IllegalStateException("이미지 파일명이 존재하지 않습니다.");
        }

        String uploadPath = serverPath + imageFolder;

        // uuid 이용 파일 디렉토리 계층화
        String uuid = UUID.randomUUID().toString();
        String uuidPath = uuid.replace("-", File.separator);

        // 파일 업로드 경로 생성
        File transferPath = new File(uploadPath, uuidPath);
        if (!transferPath.exists()) {
            transferPath.mkdirs();
        }

        // 파일 업로드
        File transferFile = new File(transferPath, fileName);
        multipartFile.transferTo(transferFile);

        // 파일 업로드 상대 경로 반화
        return uuidPath + "/" + fileName;
    }

    @Override
    public boolean deleteImage(String imagePath) throws IOException {

        Files.delete(Path.of(imagePath));

        return true;
    }

}
