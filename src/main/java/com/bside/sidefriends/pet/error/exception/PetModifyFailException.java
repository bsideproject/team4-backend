package com.bside.sidefriends.pet.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class PetModifyFailException extends BusinessException {

    public PetModifyFailException(String message) {
        super(message, ResponseCode.P_MODIFY_FAIL);
    }
}
