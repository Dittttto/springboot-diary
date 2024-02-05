package com.example.diary.domain.schedule.controller.request;

import jakarta.validation.constraints.NotNull;

public record ScheduleUpdateRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String title,
        @NotNull(message = "{NotNull}") String content,
        @NotNull(message = "{NotNull}") String password
) {
}
