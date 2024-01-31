package com.example.diary.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomJwtException extends RuntimeException {
    private final JwtErrorCode errorCode;
    private final String message;

    public CustomJwtException(JwtErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return String.format("[ERROR] %s", errorCode.getMessage());
        }

        return String.format("[ERROR] %s", message);
    }
}
