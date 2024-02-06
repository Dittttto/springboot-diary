package com.example.diary.domain.schedule.dto.service;

import com.example.diary.domain.member.model.Member;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class ScheduleCreateDTO {
    private final String title;
    private final String content;
    private final String password;
    private final Boolean isDone;
    private final Boolean isPrivate;
    private final Member member;
    private final Member assignedMember;

    public ScheduleCreateDTO(
            String title,
            String content,
            String password,
            Boolean isDone,
            Boolean isPrivate,
            Member member,
            Member assignedMember
    ) {
        this.isDone = isDone;
        this.isPrivate = isPrivate;
        validation(title, content, password, member);
        this.title = title;
        this.content = content;
        this.password = password;
        this.member = member;
        this.assignedMember = assignedMember;
    }

    private void validation(
            String title,
            String content,
            String password,
            Member member
    ) {
        checkTitleLength(title);
        checkContentLength(content);
        checkPasswordLength(password);
        checkIsNull(member);
    }

    // TODO refactoring: concat checkIsNull
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

    private void checkIsNull(Member member) {
        if (member == null) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD_EXCEPTION);
        }
    }
}
