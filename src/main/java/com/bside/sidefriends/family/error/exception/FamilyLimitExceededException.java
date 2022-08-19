package com.bside.sidefriends.family.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyLimitExceededException extends BusinessException {

    public FamilyLimitExceededException() {
        super(ResponseCode.F_MAX_NUMBER_EXCEEDED);
    }
}
