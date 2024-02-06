package com.example.diary.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequestDTO(
        @NotNull(message = "{NotNull}") String content,
        @NotNull(message = "{NotNull}") Long scheduleId
) {
}
