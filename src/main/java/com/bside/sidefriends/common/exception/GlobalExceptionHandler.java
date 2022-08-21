package com.bside.sidefriends.common.exception;

import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
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

    /**
     * Bad Request 에러: 클라이언트 입력값이 올바르지 않은 경우
     * - 클라이언트 파라미터 자료형이 올바르지 않은 경우
     * - 클라이언트 요청 바디 데이터가 정해진 형식에 맞지 않은 경우
     * - 클라이언트 요청 바디 데이터가 올바른 json 데이터가 아닌 경우
     */

    // TODO: method argument not valid + constraint violation 둘 다 있는 경우? - IR
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // validation을 통과하지 못한 필드
        Map<String, String> fieldErrors = new HashMap<>();
        e.getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));


        ResponseDto<Map<String, String>> responseDto
                = ResponseDto.onFailWithData(ResponseCode.INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {

        // validation을 통과하지 못한 필드
        Map<String, String> fieldErrors = new HashMap<>();
        e.getConstraintViolations()
                .forEach(error -> fieldErrors.put(error.getPropertyPath().toString(), error.getMessage()));

        ResponseDto<Map<String, String>> responseDto
                = ResponseDto.onFailWithData(ResponseCode.INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        ResponseDto<String> responseDto
                = ResponseDto.onFailWithData(ResponseCode.INVALID_INPUT, e.getCause().getLocalizedMessage());

        return ResponseEntity.ok().body(responseDto);
    }

    /**
     * Busine
     */
}
