package com.bside.sidefriends.symptom.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class SymptomNotSupportedException extends BusinessException {

    public SymptomNotSupportedException(String message) {
        super(message, ResponseCode.C_SYMPTOM_NOT_SUPPORTED);
    }
}
