package com.example.diary.controller.request;

public record ScheduleUpdateRequestDTO(Long id, String title, String content,
                                       String author, String password) {
}
