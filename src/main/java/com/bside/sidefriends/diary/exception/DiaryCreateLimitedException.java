package com.bside.sidefriends.diary.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class DiaryCreateLimitedException extends BusinessException {

    public DiaryCreateLimitedException() {
        super(ResponseCode.C_DIARY_LIMIT_EXCEEDED);
    }

}
