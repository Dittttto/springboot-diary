package com.example.diary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TITLE_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "제목은 최소 3자 이상 최대 30자 이하여야 합니다."),
    CONTENT_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "내용은 최소 3자 이상 최대 300자 이하여야 합니다."),
    PASSWORD_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호는 최소 3자 이상 최대 10자 이하여야 합니다."),
    AUTHOR_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "작성자 항목은 필수 입니다."),
    REQUIRED_FIELD_EXCEPTION(HttpStatus.BAD_REQUEST, "필수 항목입니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "잘못된 접근입니다."),
    PASSWORD_INVALID_EXCEPTION(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
