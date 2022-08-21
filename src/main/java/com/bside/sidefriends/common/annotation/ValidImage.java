package com.bside.sidefriends.common.annotation;

import com.bside.sidefriends.common.validator.ImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface ValidImage {

    String message() default "올바르지 않은 이미지 파일입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
