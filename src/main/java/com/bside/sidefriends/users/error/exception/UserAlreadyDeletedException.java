package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserAlreadyDeletedException extends BusinessException {

    public UserAlreadyDeletedException() {
        super(ResponseCode.U_ENTITY_DELETED);
    }

    public UserAlreadyDeletedException(String message) {
        super(message, ResponseCode.U_ENTITY_DELETED);
    }

}
