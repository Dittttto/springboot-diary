package com.example.diary.global.exception;

import com.example.diary.global.web.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // update 가 필요하다
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException e) {
        return new ResponseEntity<>(ResponseDTO.error(e.getErrorCode().getStatus(), e.getMessage()), e.getErrorCode().getStatus());
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<?> customJwtExceptionHandler(CustomJwtException e) {
        return new ResponseEntity<>(ResponseDTO.error(e.getErrorCode().getStatus(), e.getMessage()), e.getErrorCode().getStatus());

    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> customValidationExceptionHandler(CustomValidationException e) {
        return new ResponseEntity<>(ResponseDTO.error(HttpStatus.BAD_REQUEST, e.getErrors()), HttpStatus.BAD_REQUEST);
    }
}
