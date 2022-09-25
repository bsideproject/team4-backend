package com.bside.sidefriends.family.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class FamilyMemberNotFoundException extends BusinessException {

    public FamilyMemberNotFoundException(BusinessException businessException) {
        super(ResponseCode.F_MEMBER_NOT_FOUND, businessException);
    }

}
