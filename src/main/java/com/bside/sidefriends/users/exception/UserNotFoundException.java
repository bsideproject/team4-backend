package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ResponseCode.U_ENTITY_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message, ResponseCode.U_ENTITY_NOT_FOUND);
    }

}
