package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserDeleteFailException extends BusinessException {

    public UserDeleteFailException(BusinessException businessException) {
        super(ResponseCode.U_DELETE_FAIL, businessException);

    }

}
