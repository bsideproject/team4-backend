package com.bside.sidefriends.users.service.util;

import com.bside.sidefriends.common.util.ImageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LocalImageHandler implements ImageHandler {

    @Override
    public String saveImage(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        if (fileName == null) {
            throw new IllegalStateException("이미지 파일명이 존재하지 않습니다.");
        }

        // TODO: path 정규화(파일 시스템에 따라 경로 다를 수 있음)
        String serverPath = System.getProperty("user.dir");
        String uploadFolder = "/src/main/resources/static/userImages";
        String uploadPath = serverPath + uploadFolder;

        // 업로드 날짜 별 파일 디렉토리 계층화
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String datePath = sdf.format(date).replace("-", File.separator);

        // uuid 이용 파일 디렉토리 계층화
        String uuid = UUID.randomUUID().toString();
        String uuidPath = uuid.toString().replace("-", File.separator);

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
    public boolean deleteImage(Long imageId) {
        return false;
    }
}
