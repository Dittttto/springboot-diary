package com.example.diary.domain.schedule.dto.service;

import com.example.diary.domain.comment.dto.service.CommentInfoDTO;
import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.domain.schedule.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleInfoDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final Boolean isPrivate;
    private final Boolean isDone;
    private final Set<CommentInfoDTO> comments;
    private final MemberInfoDTO author;
    private final MemberInfoDTO assignedMember;
    private final LocalDateTime createdAt;

    public static ScheduleInfoDTO from(Schedule schedule) {
        return ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .isPrivate(schedule.getIsPrivate())
                .isDone(schedule.getIsDone())
                .author(MemberInfoDTO.from(schedule.getOwner()))
                .assignedMember(schedule.getAssignedMember() != null
                        ? MemberInfoDTO.from(schedule.getAssignedMember())
                        : null)
                .comments(extractRootComment(schedule.getComments()))
                .createdAt(schedule.getCreatedAt())
                .build();
    }

    private static Set<CommentInfoDTO> extractRootComment(Set<Comment> comments) {
        Map<Long, CommentInfoDTO> childCommentMap = comments.stream()
                .map(CommentInfoDTO::from)
                .collect(Collectors.toMap(CommentInfoDTO::getCommentId, Function.identity()));
        return childCommentMap.values().stream()
                .filter(comment -> comment.getParentCommentId() == null)
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator
                                .comparing(CommentInfoDTO::getCreatedAt)
                                .reversed()
                                .thenComparing(CommentInfoDTO::getCommentId)
                        )
                ));
    }
}
