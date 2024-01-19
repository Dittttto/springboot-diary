package com.example.diary.service.dto;

import com.example.diary.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleInfoDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDate createdAt;

    public static ScheduleInfoDTO from(Schedule schedule) {
        return ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .author(schedule.getAuthor())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
