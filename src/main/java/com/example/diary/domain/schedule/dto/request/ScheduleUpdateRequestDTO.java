package com.example.diary.domain.schedule.dto.request;

import jakarta.validation.constraints.NotNull;

public record ScheduleUpdateRequestDTO(
        @NotNull(message = "{NotNull}") Long id,
        @NotNull(message = "{NotNull}") String title,
        @NotNull(message = "{NotNull}") String content,
        @NotNull(message = "{NotNull}") String password,
        @NotNull(message = "{NotNull}") Boolean isDone,
        @NotNull(message = "{NotNull}") Boolean isPrivate,
        Long assignedMemberId
) {
}
