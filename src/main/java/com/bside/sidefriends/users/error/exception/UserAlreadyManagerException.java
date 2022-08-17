package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;

public class UserAlreadyManagerException extends BusinessException {
    public UserAlreadyManagerException() {
        super(ResponseCode.U_ENTITY_WITH_MANAGER_ROLE);
    }

    public UserAlreadyManagerException(String message) {
        super(message, ResponseCode.U_ENTITY_WITH_MANAGER_ROLE);
    }
}
