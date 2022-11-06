package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException() {
        super(ResponseCode.U_ENTITY_DUPLICATED);
    }

}
