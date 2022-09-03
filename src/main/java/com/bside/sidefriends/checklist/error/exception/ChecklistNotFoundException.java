package com.bside.sidefriends.checklist.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class ChecklistNotFoundException extends BusinessException {

    public ChecklistNotFoundException() {
        super(ResponseCode.CHECKLIST_NOT_FOUND);
    }

    public ChecklistNotFoundException(String message) {
        super(message, ResponseCode.CHECKLIST_NOT_FOUND);
    }

}
