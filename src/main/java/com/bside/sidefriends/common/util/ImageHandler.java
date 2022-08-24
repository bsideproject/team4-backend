package com.bside.sidefriends.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageHandler {

    String saveImage(MultipartFile multipartFile) throws IOException;

    boolean deleteImage(String imagePath) throws IOException;
}
