package com.example.diary.domain.comment.controller.dto;

import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequestDTO(
        @NotNull(message = "{NotNull}") String content,
        @NotNull(message = "{NotNull}") Long scheduleId
) {
}
