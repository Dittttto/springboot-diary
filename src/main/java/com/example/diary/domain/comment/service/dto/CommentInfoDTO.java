package com.example.diary.domain.comment.service.dto;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@AllArgsConstructor
public class CommentInfoDTO {
    private final Long commentId;
    private final MemberInfoDTO memberId;
    private final Long scheduleId;
    private final String content;
    private Long parentCommentId;
    private Set<CommentInfoDTO> childComments;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentInfoDTO from(Comment comment) {
        return new CommentInfoDTO(
                comment.getId(),
                comment.getMemberInfo(),
                comment.getScheduleId(),
                comment.getContent(),
                comment.getParentCommentId(),
                comment.getChildComment().stream().map(CommentInfoDTO::from).collect(Collectors.toSet()),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
