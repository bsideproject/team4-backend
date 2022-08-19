package com.bside.sidefriends.common.exception;

import com.bside.sidefriends.common.response.ResponseCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;
    private final List<BusinessException> nestedExceptions = new ArrayList<>();

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public BusinessException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseCode responseCode, BusinessException e) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.nestedExceptions.add(e);
    }

}

