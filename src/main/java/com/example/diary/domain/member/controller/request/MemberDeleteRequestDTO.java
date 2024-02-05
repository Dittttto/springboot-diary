package com.example.diary.domain.member.controller.request;

import jakarta.validation.constraints.NotNull;

public record MemberDeleteRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String password
) {
}
