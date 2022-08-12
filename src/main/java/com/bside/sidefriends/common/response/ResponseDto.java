package com.bside.sidefriends.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private String code;
    private String message;
    private T data;

    private ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 성공 응답 객체 생성
     * @param responseCode {@link ResponseCode} 응답 코드 및 메시지
     * @return 성공 응답 DTO 객체
     */
    public static ResponseDto<ResponseCode> onSuccessWithoutData(ResponseCode responseCode) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage());
    }

    /**
     * 성공 응답 객체 생성
     * @param responseCode {@link ResponseCode} 응답 코드 및 메시지
     * @param data 성공 응답에서 반환할 데이터
     * @return 성공 응답 DTO 객체
     */
    public static <T> ResponseDto<T> onSuccessWithData(ResponseCode responseCode, T data) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage(), data);
    }

    /**
     * 실패 응답 객체 생성
     * @param responseCode {@link ResponseCode} 응답 코드 및 메시지
     * @return 실패 응답 DTO 객체
     */
    public static ResponseDto<ResponseCode> onFailWithoutData(ResponseCode responseCode) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage());
    }

    /**
     * 실패 응답 객체 반환 메서드
     * @param responseCode {@link ResponseCode} 응답 코드 및 메시지
     * @param data 실패 응답에서 반환할 데이터
     * @return 실패 응답 DTO 객체
     */
    public static <T> ResponseDto<T> onFailWithData(ResponseCode responseCode, T data) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage(), data);
    }

}
