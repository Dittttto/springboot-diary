package com.example.diary.exception;

import com.example.diary.controller.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<String>> customExceptionHandler(CustomException e) {
        return ResponseEntity.ok(ResponseDTO.error(e.getErrorCode(), e.getMessage()));
    }
}
