package com.example.diary.domain.member.dto.request;

import com.example.diary.domain.member.util.PatternUtil;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberCreateRequestDTO(
        @NotNull(message = "{NotNull}")
        @Pattern(regexp = PatternUtil.EMAIL_PATTERN,
                message = "이메일 형식에 맞지 않습니다.")
        String email,
        @NotNull(message = "{NotNull}")
        String username,
        @NotNull(message = "{NotNull}")
        @Pattern(regexp = PatternUtil.PASSWORD_PATTERN,
                message = "비밀번호 형식에 맞지 않습니다.")
        String password
) {
}
