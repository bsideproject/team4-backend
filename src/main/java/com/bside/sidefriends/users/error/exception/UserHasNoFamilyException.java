package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserHasNoFamilyException extends BusinessException {

    public UserHasNoFamilyException() {
        super(ResponseCode.U_ENTITY_WITHOUT_FAMILY);
    }

}
