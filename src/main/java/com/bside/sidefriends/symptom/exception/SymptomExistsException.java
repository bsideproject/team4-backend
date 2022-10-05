package com.bside.sidefriends.symptom.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class SymptomExistsException extends BusinessException {
    public SymptomExistsException() {
        super(ResponseCode.C_SYMPTOM_ENTITY_EXISTS);
    }
}
