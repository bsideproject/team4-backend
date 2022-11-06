package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyNotFoundException extends BusinessException {

    public FamilyNotFoundException() {
        super(ResponseCode.F_ENTITY_NOT_FOUND);
    }

}
