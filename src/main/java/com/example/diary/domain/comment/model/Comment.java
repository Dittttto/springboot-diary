package com.example.diary.domain.comment.model;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private MemberInfoDTO memberInfo;
    private Long scheduleId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Comment from(CommentEntity entity){
        return new Comment(
                entity.getId(),
                MemberInfoDTO.from(Member.from(entity.getMember())),
                entity.getSchedule().getId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public CommentEntity toEntity(Member member, Schedule schedule) {
        return new CommentEntity(
                id,
                schedule.toEntity(),
                member.toEntity(),
                content,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Comment update(Member member, String content) {
        checkAuthority(member);

        return new Comment(
                id,
                memberInfo,
                scheduleId,
                content,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public void delete(Member member) {
        checkAuthority(member);
    }

    private void checkAuthority(Member member) {
        if (member.hasNotAuthority(memberInfo.getEmail())){
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }
    }


}
