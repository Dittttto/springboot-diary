package com.example.diary.domain.schedule.dto.service;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;
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
    private Boolean isDone;
    private Boolean isPrivate;
    private Member assignedMember;


    public static ScheduleUpdateDTO from(Schedule schedule) {
        return ScheduleUpdateDTO.builder()
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .password(schedule.getPassword())
                .isDone(schedule.getIsDone())
                .isPrivate(schedule.getIsPrivate())
                .assignedMember(schedule.getAssignedMember())
                .build();
    }
}
