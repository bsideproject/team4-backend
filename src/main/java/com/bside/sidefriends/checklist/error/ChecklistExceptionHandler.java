package com.bside.sidefriends.checklist.error;

import com.bside.sidefriends.checklist.controller.ChecklistController;
import com.bside.sidefriends.common.exception.BusinessException;
import com.bside.sidefriends.common.response.ResponseCode;
import com.bside.sidefriends.common.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackageClasses = ChecklistController.class)
public class ChecklistExceptionHandler {

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
