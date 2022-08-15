package com.bside.sidefriends.users.error.exception;

import com.bside.sidefriends.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public BusinessException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}
