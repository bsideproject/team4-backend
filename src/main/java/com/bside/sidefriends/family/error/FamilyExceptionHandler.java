package com.bside.sidefriends.family.error;

import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class FamilyExceptionHandler {

    // TODO: Map 사용하는 에러 처리 방식 좋은 방법인지 고민해 볼 것
    // TODO: 에러 핸들러 common 패키지로 올리는 방안?
    // TODO: ResponseDto의 반복적 선언 및 할당의 변경 방안?
    // TODO: unbound parameter

    private String getErrorMessage(ResponseCode errorCode, BusinessException e) {
        if (e.getMessage().equals(errorCode.getMessage())) {
            return errorCode.getMessage();
        } else {
            return e.getMessage();
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<?>> handleBusinessException(BusinessException e) {

        String message = e.getMessage();
        ResponseCode responseCode = e.getResponseCode();
        List<BusinessException> nestedExceptions = e.getNestedExceptions();

        if (!nestedExceptions.isEmpty()) {

            List<ErrorData> errorDataList = nestedExceptions.stream()
                    .map(nestedException -> {
                        ResponseCode errorResponseCode = nestedException.getResponseCode();
                        return new ErrorData(errorResponseCode.getCode(), getErrorMessage(errorResponseCode, nestedException));
                    })
                    .collect(Collectors.toList());

            ResponseDto<List<ErrorData>> responseDto = ResponseDto.onFailWithData(responseCode, errorDataList);
            return ResponseEntity.ok().body(responseDto);
        }

        if (!message.equals(responseCode.getMessage())) {

            ErrorData errorData = new ErrorData(message);

            ResponseDto<ErrorData> responseDto = ResponseDto.onFailWithData(responseCode, errorData);
            return ResponseEntity.ok().body(responseDto);

        }

        ResponseDto<ResponseCode> responseDto = ResponseDto.onFailWithoutData(responseCode);
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
                = ResponseDto.onFailWithData(ResponseCode.F_INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {

        // validation을 통과하지 못한 필드
        Map<String, String> fieldErrors = new HashMap<>();
        e.getConstraintViolations()
                .forEach(error -> fieldErrors.put(error.getPropertyPath().toString(), error.getMessage()));

        ResponseDto<Map<String, String>> responseDto
                = ResponseDto.onFailWithData(ResponseCode.F_INVALID_INPUT, fieldErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        ResponseDto<String> responseDto
                = ResponseDto.onFailWithData(ResponseCode.F_INVALID_INPUT, e.getCause().getLocalizedMessage());

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        // Type Mismatch 파라미터
        Map<String, String> parameterErrors = new HashMap<>();

        String mismatchParameter = e.getParameter().getParameterName();
        String requiredType = Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        parameterErrors.put("parameter", mismatchParameter);
        parameterErrors.put("required", requiredType);

        ResponseDto<Map<String, String>> responseDto = ResponseDto.onFailWithData(ResponseCode.F_INVALID_INPUT, parameterErrors);

        return ResponseEntity.ok().body(responseDto);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ErrorData {

        private String errorCode;
        private final String errorMessage;

        ErrorData(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        ErrorData(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    }

}
