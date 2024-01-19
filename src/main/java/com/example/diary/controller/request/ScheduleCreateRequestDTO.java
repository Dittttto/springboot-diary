package com.example.diary.controller.request;

public record ScheduleCreateRequestDTO(String title, String content,
                                       String password, String author) {
}
