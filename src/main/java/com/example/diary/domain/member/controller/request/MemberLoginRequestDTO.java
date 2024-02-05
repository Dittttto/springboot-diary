package com.example.diary.domain.member.controller.request;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequestDTO(
        @NotNull(message = "{NotNull}") String email,
        @NotNull(message = "{NotNull}") String password
) {
}
