package com.example.diary.global.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
    private HttpStatus code;
    private T body;

    public static ResponseDTO<String> error(HttpStatus status, String message) {
        return new ResponseDTO<>(status, message);
    }

    public static <T> ResponseDTO<T> error(HttpStatus status, T body) {
        return new ResponseDTO<>(status, body);
    }

    public static <T> ResponseDTO<T> success(T body) {
        return new ResponseDTO<>(HttpStatus.OK, body);
    }
}
