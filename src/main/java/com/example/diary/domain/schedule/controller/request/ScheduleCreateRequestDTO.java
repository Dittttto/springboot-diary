package com.example.diary.domain.schedule.controller.request;

import jakarta.validation.constraints.NotNull;

public record ScheduleCreateRequestDTO(
        @NotNull(message = "{NotNull}") String title,
        @NotNull(message = "{NotNull}") String content,
        @NotNull(message = "{NotNull}") String password,
        @NotNull(message = "{NotNull}") String author
) {
}
