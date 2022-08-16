package com.bside.sidefriends.family.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyNotFoundException extends BusinessException {

    public FamilyNotFoundException() {
        super(ResponseCode.F_ENTITY_NOT_FOUND);
    }

}
