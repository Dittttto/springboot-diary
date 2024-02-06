package com.example.diary.domain.comment.model;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private MemberInfoDTO memberInfo;
    private Long scheduleId;
    private Long parentCommentId;
    private Set<Comment> childComment;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Comment from(CommentEntity entity) {
        Comparator<Comment> childCommentComparator = Comparator
                .comparing(Comment::getCreatedAt)
                .thenComparing(Comment::getId);
        TreeSet<Comment> comments = new TreeSet<>(childCommentComparator);
        entity.getChildComment().forEach(childComment -> comments.add(Comment.from(childComment)));

        return new Comment(
                entity.getId(),
                MemberInfoDTO.from(Member.from(entity.getMember())),
                entity.getSchedule().getId(),
                entity.getParentCommentId(),
                comments,
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public boolean isNotOwner(Member member) {
        if (member.hasNotAuthority(memberInfo.getEmail())) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return true;
    }
}
