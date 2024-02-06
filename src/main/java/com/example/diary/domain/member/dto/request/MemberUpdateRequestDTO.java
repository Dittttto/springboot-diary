package com.example.diary.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String username,
        @NotNull(message = "{NotNull}") String password,
        @NotNull(message = "{NotNull}") Boolean isAdmin
) {
}
