package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyDeleteFailException extends BusinessException {
    public FamilyDeleteFailException() {
        super(ResponseCode.F_DELETE_NOT_ALLOWED);
    }

    public FamilyDeleteFailException(String message) {
        super(message, ResponseCode.F_DELETE_NOT_ALLOWED);
    }
}
