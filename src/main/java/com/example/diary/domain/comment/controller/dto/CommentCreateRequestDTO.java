package com.example.diary.domain.comment.controller.dto;

public record CommentCreateRequestDTO(Long scheduleId,
                                      String content) {
}
