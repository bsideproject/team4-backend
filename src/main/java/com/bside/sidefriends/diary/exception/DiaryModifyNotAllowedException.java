package com.bside.sidefriends.diary.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class DiaryModifyNotAllowedException extends BusinessException {

    public DiaryModifyNotAllowedException() {
        super(ResponseCode.C_DIARY_MODIFY_NOT_ALLOWED);
    }

}
