package com.example.diary.domain.member.controller.request;

import jakarta.validation.constraints.NotNull;

public record MemberLogoutRequestDTO(
        @NotNull(message = "{NotNull}") Long id
) {
}
