package com.example.diary.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentCreateRequestDTO(
        @NotNull(message = "{NotNull}") Long scheduleId,
        @NotNull(message = "{NotNull}") String content,
        Long parentCommentId
) {
}
