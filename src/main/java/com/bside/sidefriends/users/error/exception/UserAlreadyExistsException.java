package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException() {
        super(ResponseCode.U_ENTITY_DUPLICATED);
    }

}
