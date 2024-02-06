package com.example.diary.domain.comment.model;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

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

    public static Comment from(final CommentEntity entity) {
        Comparator<Comment> childCommentComparator = Comparator
                .comparing(Comment::getCreatedAt)
                .thenComparing(Comment::getId);
        TreeSet<Comment> comments = new TreeSet<>(childCommentComparator);
        entity.getChildComment().forEach(childComment ->
                comments.add(Comment.from(childComment)));

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

    public boolean isNotOwner(final Member member) {
        return !member.isSameEmail(memberInfo.getEmail());
    }
}
