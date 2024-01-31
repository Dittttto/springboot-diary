package com.example.diary.global.exception;

import com.example.diary.global.web.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<String>> customExceptionHandler(CustomException e) {
        return ResponseEntity.ok(ResponseDTO.error(e.getErrorCode().getStatus(), e.getMessage()));
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<ResponseDTO<String>> customJwtExceptionHandler(CustomJwtException e) {
        return ResponseEntity.ok(ResponseDTO.error(e.getErrorCode().getStatus(), e.getMessage()));
    }
}
