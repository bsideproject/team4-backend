package com.bside.sidefriends.users.service.util;

import com.bside.sidefriends.common.util.ImageHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class NcpObjectStorageImageHandler implements ImageHandler {
    @Override
    public String saveImage(MultipartFile multipartFile) throws IOException {
        return null;
    }

    @Override
    public boolean deleteImage(String imagePath) throws IOException {
        return false;
    }
}
