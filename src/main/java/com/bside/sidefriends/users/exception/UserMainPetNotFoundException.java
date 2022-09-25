package com.bside.sidefriends.users.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class UserMainPetNotFoundException extends BusinessException {
    public UserMainPetNotFoundException() {
        super(ResponseCode.U_MAIN_PET_NOT_FOUND);
    }
}