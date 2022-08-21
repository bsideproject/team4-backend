package com.bside.sidefriends.users.error;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.users.controller.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    // TODO: Map 사용하는 에러 처리 방식 좋은 방법인지 고민해 볼 것
    // TODO: ResponseDto의 반복적 선언 및 할당의 변경 방안?
    // TODO: unbound parameter

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<?>> handleBusinessException(BusinessException e) {

        String message = e.getMessage();
        ResponseCode responseCode = e.getResponseCode();
        final ResponseDto<?> responseDto;

        if (message.equals(responseCode.getMessage())) {
            responseDto = ResponseDto.onFailWithoutData(responseCode);
        } else {

            Map<String, String> errorData = new HashMap<>();
            errorData.put("errorMessage", message);

            responseDto = ResponseDto.onFailWithData(responseCode, errorData);
        }

        return ResponseEntity.ok().body(responseDto);
    }

}
