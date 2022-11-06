package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserHasFamilyException extends BusinessException {

    public UserHasFamilyException() {
        super(ResponseCode.F_MEMBER_DUPLICATED);
    }

    public UserHasFamilyException(String message) {
        super(message, ResponseCode.F_MEMBER_DUPLICATED);
    }
}
