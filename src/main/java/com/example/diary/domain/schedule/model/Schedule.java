package com.example.diary.domain.schedule.model;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String content;
    private String password;
    private Boolean isDone;
    private Boolean isPrivate;
    private Member assignedMember;
    private Member owner;
    private Set<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Schedule from(final ScheduleEntity entity) {
        return new Schedule(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPassword(),
                entity.getIsDone(),
                entity.getIsPrivate(),
                entity.getAssignedMember() != null
                        ? Member.from(entity.getAssignedMember())
                        : null,
                Member.from(entity.getOwner()),
                entity.getCommentEntities().stream()
                        .map(Comment::from)
                        .collect(Collectors.toSet()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public ScheduleEntity toEntity() {
        return new ScheduleEntity(
                id,
                title,
                content,
                password,
                isDone,
                isPrivate,
                assignedMember != null ? assignedMember.toEntity() : null,
                owner.toEntity(),
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Schedule update(
            final String title,
            final String content,
            final String password,
            final Boolean isDone,
            final Boolean isPrivate,
            final Member member,
            final Member assignedMember
    ) {
        hasAuthorization(password, member);

        return new Schedule(
                id,
                title,
                content,
                password,
                isDone,
                isPrivate,
                assignedMember,
                owner,
                comments,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public boolean hasAuthorization(
            final String password,
            final Member member
    ) {
        if (!this.owner.equals(member)) {
            // 조금 더 적절한 것으로 바꿔야 한다.
            throw new CustomException(ErrorCode.UN_AUTHORIZATION_EXCEPTION);
        }

        if (!this.password.equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return true;
    }
}
