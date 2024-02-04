package com.example.diary.domain.comment.model;

import com.example.diary.domain.infrastructure.entity.CommentEntity;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private ScheduleEntity schedule;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Comment of() {
        // TODO: implement
        return null;
    }

    public static CommentEntity toEntity() {
        // TODO: implement
        return null;
    }


}
