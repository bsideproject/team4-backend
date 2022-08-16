package com.bside.sidefriends.family.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyAlreadyDeletedException extends BusinessException {

    public FamilyAlreadyDeletedException() {
        super(ResponseCode.F_ENTITY_DELETED);
    }
}
