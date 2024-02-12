package com.example.diary.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효한 토큰이 아닙니다."),
    JWT_SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED,"Invalid JWT signature"),
    JWT_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED,"JWT token is expired"),
    UN_SUPPORTED_TOKEN_TYPE_EXCEPTION(HttpStatus.UNAUTHORIZED,"Unsupported JWT token type"),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED,"Invalid JWT token");

    private final HttpStatus status;
    private final String message;
}
