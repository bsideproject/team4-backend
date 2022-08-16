package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class UserHasFamilyException extends BusinessException {

    public UserHasFamilyException() {
        super(ResponseCode.U_ENTITY_WITH_FAMILY);
    }
}
