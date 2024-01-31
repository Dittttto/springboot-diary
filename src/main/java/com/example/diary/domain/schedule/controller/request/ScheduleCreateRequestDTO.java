package com.example.diary.domain.schedule.controller.request;

public record ScheduleCreateRequestDTO(String title, String content,
                                       String password, String author) {
}
