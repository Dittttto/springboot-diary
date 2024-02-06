package com.example.diary.domain.schedule.dto.request;

import jakarta.validation.constraints.NotNull;

public record ScheduleDeleteRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String password
) {
}
