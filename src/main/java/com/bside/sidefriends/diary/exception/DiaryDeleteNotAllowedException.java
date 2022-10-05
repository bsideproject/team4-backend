package com.bside.sidefriends.diary.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class DiaryDeleteNotAllowedException extends BusinessException {

    public DiaryDeleteNotAllowedException() {
        super(ResponseCode.C_DIARY_DELETE_NOT_ALLOWED);
    }

}
