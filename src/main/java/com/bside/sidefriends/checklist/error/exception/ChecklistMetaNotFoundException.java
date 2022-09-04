package com.bside.sidefriends.checklist.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class ChecklistMetaNotFoundException extends BusinessException {

    public ChecklistMetaNotFoundException() {
        super(ResponseCode.CHECKLIST_META_NOT_FOUND);
    }

    public ChecklistMetaNotFoundException(String message) {
        super(message, ResponseCode.CHECKLIST_META_NOT_FOUND);
    }
}
