package com.example.diary.controller.response;

import com.example.diary.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
    private HttpStatus resultCode;
    private T body;

    public static ResponseDTO<String> error(ErrorCode errorCode, String message) {
        return new ResponseDTO<>(errorCode.getStatus(), message);
    }

    public static <T> ResponseDTO<T> success(T body) {
        return new ResponseDTO<>(HttpStatus.OK, body);
    }
}
