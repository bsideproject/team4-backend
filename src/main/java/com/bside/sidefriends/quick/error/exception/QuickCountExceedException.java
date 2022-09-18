package com.bside.sidefriends.quick.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class QuickCountExceedException extends BusinessException {
    public QuickCountExceedException(String message) {
        super(message, ResponseCode.QUICK_COUNT_EXCEED);
    }
}
