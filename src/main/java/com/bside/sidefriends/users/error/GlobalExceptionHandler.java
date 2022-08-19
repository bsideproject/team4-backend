package com.bside.sidefriends.users.error;

import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.users.error.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<ResponseCode>> handleBusinessException(BusinessException e) {

        ResponseDto<ResponseCode> responseDto = ResponseDto.onFailWithoutData(e.getResponseCode());
        return ResponseEntity.ok().body(responseDto);
    }

    // TODO: method argument not valid + constraint violation 둘 다 있는 경우?
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // validation을 통과하지 못한 필드
        Map<String, String> fieldErrors = new HashMap<>();
        e.getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));


        ResponseDto<Map<String, String>> responseDto
                = ResponseDto.onFailWithData(ResponseCode.U_INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {

        // validation을 통과하지 못한 필드
        Map<String, String> fieldErrors = new HashMap<>();
        e.getConstraintViolations()
                .forEach(error -> fieldErrors.put(error.getPropertyPath().toString(), error.getMessage()));

        ResponseDto<Map<String, String>> responseDto
                = ResponseDto.onFailWithData(ResponseCode.U_INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        ResponseDto<String> responseDto
                = ResponseDto.onFailWithData(ResponseCode.U_INVALID_INPUT, e.getCause().getLocalizedMessage());

        return ResponseEntity.ok().body(responseDto);
    }

}
