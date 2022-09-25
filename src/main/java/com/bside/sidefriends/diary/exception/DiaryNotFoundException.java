package com.bside.sidefriends.diary.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class DiaryNotFoundException extends BusinessException {

    public DiaryNotFoundException() {
        super(ResponseCode.C_DIARY_ENTITY_NOT_FOUND);
    }

}
