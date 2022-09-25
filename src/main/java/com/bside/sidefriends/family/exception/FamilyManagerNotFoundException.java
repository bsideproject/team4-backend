package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyManagerNotFoundException extends BusinessException {

    public FamilyManagerNotFoundException(BusinessException businessException) {
        super(ResponseCode.F_MANAGER_NOT_FOUND, businessException);
    }
}
