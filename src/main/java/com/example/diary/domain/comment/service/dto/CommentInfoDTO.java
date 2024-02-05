package com.example.diary.domain.comment.service.dto;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentInfoDTO {
    private final Long commentId;
    private final MemberInfoDTO memberId;
    private final Long scheduleId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentInfoDTO from(Comment comment) {
        return new CommentInfoDTO(
                comment.getId(),
                comment.getMemberInfo(),
                comment.getScheduleId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
