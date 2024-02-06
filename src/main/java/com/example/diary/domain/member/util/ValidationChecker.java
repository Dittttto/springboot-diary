package com.example.diary.domain.member.util;

import com.example.diary.global.exception.CustomValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationChecker {
    private ValidationChecker() {}

    public static void hasError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            throw new CustomValidationException(errors);
        }
    }
}
