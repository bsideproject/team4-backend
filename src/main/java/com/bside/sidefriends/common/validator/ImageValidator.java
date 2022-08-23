package com.bside.sidefriends.common.validator;

import com.bside.sidefriends.common.annotation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        String contentType = file.getContentType();

        if (file.isEmpty() || contentType == null) {
            context.buildConstraintViolationWithTemplate("이미지 파일이 비어 있습니다.")
                    .addConstraintViolation();
            return false;
        }

        if (!isValidContentType(contentType)) {
            context.buildConstraintViolationWithTemplate("이미지 파일 형식이 아닙니다.")
                    .addConstraintViolation();
            return false;
        }

        return true;

    }

    private static boolean isValidContentType(String contentType) {
        return contentType.startsWith("image");
    }


}
