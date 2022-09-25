package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyManagerRequiredException extends BusinessException {

    public FamilyManagerRequiredException(String message) {
        super(message, ResponseCode.F_ROLE_MANAGER_REQUIRED);
    }

    public FamilyManagerRequiredException(BusinessException businessException) {
        super(ResponseCode.F_ROLE_MANAGER_REQUIRED, businessException);
    }
}
