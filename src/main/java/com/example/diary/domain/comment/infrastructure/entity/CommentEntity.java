package com.example.diary.domain.comment.infrastructure.entity;

import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Table(name = "schedule_comment")
@Entity
@SQLDelete(sql = "update schedule_comment set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private ScheduleEntity schedule;

    @ManyToOne(optional = false)
    private MemberEntity member;

    @Setter
    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    public CommentEntity(
            ScheduleEntity schedule,
            MemberEntity member,
            String content
    ) {
        this.schedule = schedule;
        this.member = member;
        this.content = content;
    }
}
