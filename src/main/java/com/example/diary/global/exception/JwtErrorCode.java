package com.example.diary.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효한 토큰이 아닙니다.");

    private final HttpStatus status;
    private final String message;
}
