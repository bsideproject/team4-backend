package com.bside.sidefriends.family.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyManagerRequiredException extends BusinessException {

    public FamilyManagerRequiredException() {
        super(ResponseCode.F_ROLE_MANAGER_REQUIRED);
    }
}
