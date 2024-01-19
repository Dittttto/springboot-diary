package com.example.diary.service.dto;

import com.example.diary.exception.CustomException;
import com.example.diary.exception.ErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class ScheduleCreateDTO {
    private final String title;
    private final String content;
    private final String author;
    private final String password;

    public ScheduleCreateDTO(String title, String content, String author, String password) {
        validation(title, content, author, password);
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    private void validation(String title, String content, String author, String password) {
        checkTitleLength(title);
        checkContentLength(content);
        checkPasswordLength(password);
        checkHasText(author, "author");
    }

    private void checkHasText(String target, String field) {
        if (!StringUtils.hasText(target)) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD_EXCEPTION,
                    String.format("%s는 필수 작성 항목입니다.", field));
        }
    }

    private void checkTitleLength(String title) {
        checkHasText(title, "title");
        if (title.length() > 30 || title.length() < 3) {
            throw new CustomException(ErrorCode.TITLE_LENGTH_EXCEPTION);
        }
    }

    private void checkContentLength(String content) {
        checkHasText(content, "content");
        if (content.length() > 300 || content.length() < 5) {
            throw new CustomException(ErrorCode.CONTENT_LENGTH_EXCEPTION);
        }
    }

    private void checkPasswordLength(String password) {
        checkHasText(password, "password");
        if (password.length() > 10 || password.length() < 3) {
            throw new CustomException(ErrorCode.PASSWORD_LENGTH_EXCEPTION);
        }
    }
}
