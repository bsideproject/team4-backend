package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class UserHasWrongFamilyException extends BusinessException {

    public UserHasWrongFamilyException() {
        super(ResponseCode.U_ENTITY_WITH_WRONG_FAMILY);
    }
}