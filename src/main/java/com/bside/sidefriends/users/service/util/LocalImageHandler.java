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

        // TODO: path 정규화(파일 시스템에 따라 경로 다를 수 있음)
        String serverPath = System.getProperty("user.dir");
        String uploadFolder = "/src/main/resources/static/userImages";
        String uploadPath = serverPath + uploadFolder;

        // 업로드 날짜 별 파일 디렉토리 계층화
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datePath = sdf.format(date).replace("-", File.separator);

        // 이미지 id 발급
        String uuid = UUID.randomUUID().toString().replace("-", "");

        // 파일 업로드 경로 생성
        File transferPath = new File(uploadPath, datePath);
        if (!transferPath.exists()) {
            transferPath.mkdirs();
        }

        // 파일 업로드
        File transferFile = new File(transferPath, uuid);
        multipartFile.transferTo(transferFile);

        return uuid;
    }

    @Override
    public boolean deleteImage(Long imageId) {
        return false;
    }
}
