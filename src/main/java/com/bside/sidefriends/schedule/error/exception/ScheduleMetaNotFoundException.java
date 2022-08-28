package com.bside.sidefriends.schedule.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class ScheduleMetaNotFoundException extends BusinessException {

    public ScheduleMetaNotFoundException() {
        super(ResponseCode.SCHEDULE_META_NOT_FOUND);
    }

    public ScheduleMetaNotFoundException(String message) {
        super(message, ResponseCode.SCHEDULE_META_NOT_FOUND);
    }

}
