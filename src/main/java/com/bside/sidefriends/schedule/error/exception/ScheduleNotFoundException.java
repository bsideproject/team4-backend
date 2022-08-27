package com.bside.sidefriends.schedule.error.exception;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;

public class ScheduleNotFoundException extends BusinessException {

    public ScheduleNotFoundException() {
        super(ResponseCode.SCHEDULE_NOT_FOUND);
    }

    public ScheduleNotFoundException(String message) {
        super(message, ResponseCode.SCHEDULE_NOT_FOUND);
    }

}
