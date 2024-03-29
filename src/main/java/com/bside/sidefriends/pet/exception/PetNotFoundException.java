package com.bside.sidefriends.pet.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class PetNotFoundException extends BusinessException {
    public PetNotFoundException() {
        super(ResponseCode.P_ENTITY_NOT_FOUND);
    }

    public PetNotFoundException(String message) {
        super(message, ResponseCode.P_ENTITY_NOT_FOUND);
    }
}
