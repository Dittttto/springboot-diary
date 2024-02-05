package com.example.diary.domain.schedule.controller.request;

public record ScheduleUpdateRequestDTO(Long id,
                                       String title,
                                       String content,
                                       String password) {
}
