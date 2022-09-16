package com.bside.sidefriends.pet.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class PetShareFailException extends BusinessException {

    public PetShareFailException(String message) {
        super(message, ResponseCode.P_SHARE_FAIL);
    }
}
