package com.bside.sidefriends.symptom.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class SymptomNotFoundException extends BusinessException {

    public SymptomNotFoundException() {
        super(ResponseCode.C_SYMPTOM_ENTITY_NOT_FOUND);
    }
}
