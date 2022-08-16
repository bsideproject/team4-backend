package com.bside.sidefriends.family.error;

import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.error.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FamilyExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<ResponseCode>> handleBusinessException(BusinessException e) {
        ResponseDto<ResponseCode> responseDto = ResponseDto.onFailWithoutData(e.getResponseCode());
        return ResponseEntity.ok().body(responseDto);
    }


}
