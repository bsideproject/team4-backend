package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserInfoNotChangedException extends BusinessException {
    public UserInfoNotChangedException() {
        super(ResponseCode.U_ENTITY_NOT_UPDATED);
    }

    public UserInfoNotChangedException(String message) {
        super(message, ResponseCode.U_ENTITY_NOT_UPDATED);
    }

}
