package com.bside.sidefriends.common.annotation;

import com.bside.sidefriends.common.validator.UserEntityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserEntityValidator.class)
public @interface ValidUserEntity {

    String message() default "유효한 사용자가 아닙니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
