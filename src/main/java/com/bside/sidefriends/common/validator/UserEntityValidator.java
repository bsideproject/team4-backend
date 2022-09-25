package com.bside.sidefriends.common.validator;

import com.bside.sidefriends.common.annotation.ValidUserEntity;
import com.bside.sidefriends.users.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEntityValidator implements ConstraintValidator<ValidUserEntity, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        return !user.isDeleted();
    }
}
