package com.bside.sidefriends.quick.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class QuickNotFoundException extends BusinessException {
    public QuickNotFoundException(String message) {
        super(message, ResponseCode.QUICK_NOT_FOUND);
    }
}
