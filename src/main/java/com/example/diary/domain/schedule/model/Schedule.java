package com.example.diary.domain.schedule.model;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String content;
    private String password;
    private Member member;
    private List<Comment> comments;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public static Schedule from(ScheduleEntity entity) {
        return new Schedule(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPassword(),
                Member.from(entity.getMemberEntity()),
                entity.getCommentEntities().stream().map(Comment::from).toList(),
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
                member.toEntity(),
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Schedule update(String title,
                           String content,
                           String password,
                           Member member) {
        // check is owner
        if (!this.member.equals(member)) {
            // 조금 더 적절한 것으로 바꿔야 한다.
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        if (!this.password.equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return Schedule.builder()
                .id(id)
                .title(title)
                .content(content)
                .password(password)
                .member(member)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public void deleteSchedule(String password, Member member) {
        if (!this.member.equals(member)) {
            // 조금 더 적절한 것으로 바꿔야 한다.
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        if (!this.password.equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }
    }
}
