package com.example.diary.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberLogoutRequestDTO(
        @NotNull(message = "{NotNull}") Long id
) {
}
