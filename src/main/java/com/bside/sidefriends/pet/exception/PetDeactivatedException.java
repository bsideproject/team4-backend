package com.bside.sidefriends.pet.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class PetDeactivatedException extends BusinessException {
    public PetDeactivatedException() { super(ResponseCode.P_ENTITY_DEACTIVATED);}
}
