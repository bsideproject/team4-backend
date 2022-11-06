package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyMemberDeleteFailException extends BusinessException {

    public FamilyMemberDeleteFailException(String message) {
        super(message, ResponseCode.F_DELETE_MEMBER_NOT_ALLOWED);
    }

    public FamilyMemberDeleteFailException(BusinessException businessException) {
        super(ResponseCode.F_DELETE_MEMBER_NOT_ALLOWED, businessException);
    }

}
