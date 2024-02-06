package com.example.diary.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberDeleteRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String password
) {
}
