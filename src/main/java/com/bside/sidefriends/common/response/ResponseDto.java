package com.bside.sidefriends.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private String code;
    private String message;
    private T data;

    /**
     * 성공 응답 객체 반환 메서드
     * @param code {@link ResponseCode}의 응답 코드
     * @param message {@link ResponseCode}의 응답 메시지
     * @param data 성공 응답에서 반환할 데이터
     * @return 성공 응답 DTO 객체
     */
    public static <T> ResponseDto<T> onSuccess(String code, String message, T data) {
        return new ResponseDto<>(code, message, data);
    }

    /**
     * 실패 응답 객체 반환 메서드
     * @param code {@link ResponseCode}의 응답 코드
     * @param message {@link ResponseCode}의 응답 메시지
     * @param data 실패 응답에서 반환할 데이터
     * @return 실패 응답 DTO 객체
     */
    public static <T> ResponseDto<T> onFail(String code, String message, T data) {
        return new ResponseDto<>(code, message, data);
    }
}
