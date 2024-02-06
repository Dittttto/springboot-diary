package com.example.diary.domain.comment.dto.service;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;
import lombok.Getter;

@Getter
public class CommentCreateDto {
    private final Member member;
    private final Schedule schedule;
    private final String content;

    public CommentCreateDto(
            Member member,
            Schedule schedule,
            String content
    ) {
        this.member = member;
        this.schedule = schedule;
        this.content = content;
    }
}
