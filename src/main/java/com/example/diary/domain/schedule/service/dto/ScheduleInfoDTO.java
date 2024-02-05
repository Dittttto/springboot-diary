package com.example.diary.domain.schedule.service.dto;

import com.example.diary.domain.comment.service.dto.CommentInfoDTO;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import com.example.diary.domain.schedule.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleInfoDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final List<CommentInfoDTO> comments;
    private final MemberInfoDTO author;
    private final LocalDate createdAt;

    public static ScheduleInfoDTO from(Schedule schedule) {
        return ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .author(MemberInfoDTO.from(schedule.getMember()))
                .comments(schedule.getComments().stream().map(CommentInfoDTO::from).toList())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
