package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ResponseCode.U_ENTITY_NOT_FOUND);
    }

}
