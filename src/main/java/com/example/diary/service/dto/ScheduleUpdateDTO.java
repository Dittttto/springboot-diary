package com.example.diary.service.dto;

import com.example.diary.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateDTO {
    private String title;
    private String content;
    private String author;
    private String password;

    public static ScheduleUpdateDTO from(Schedule schedule) {
        return ScheduleUpdateDTO.builder()
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .author(schedule.getAuthor())
                .password(schedule.getPassword())
                .build();
    }
}
