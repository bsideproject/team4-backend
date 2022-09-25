package com.bside.sidefriends.common.exception;

import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import com.bside.sidefriends.family.controller.FamilyController;
import com.bside.sidefriends.pet.controller.PetController;
import com.bside.sidefriends.users.controller.UserController;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(basePackageClasses = {
        UserController.class,
        FamilyController.class,
        PetController.class
})
public class BusinessExceptionHandler {

    // TODO: Map 사용하는 에러 처리 방식 좋은 방법인지 고민해 볼 것
    // TODO: ResponseDto의 반복적 선언 및 할당의 변경 방안?
    // TODO: unbound parameter
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

    private String getErrorMessage(ResponseCode errorCode, BusinessException e) {
        if (e.getMessage().equals(errorCode.getMessage())) {
            return errorCode.getMessage();
        } else {
            return e.getMessage();
        }
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
