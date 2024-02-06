package com.example.diary.domain.comment.infrastructure.entity;

import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @JoinColumn(name = "scheduleId")
    @ManyToOne(optional = false)
    private ScheduleEntity schedule;

    @JoinColumn(name = "memberId")
    @ManyToOne(optional = false)
    private MemberEntity member;

    @Setter
    @Column(updatable = false)
    private Long parentCommentId;

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<CommentEntity> childComment = new LinkedHashSet<>();

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
            Long parentCommentId,
            String content
    ) {
        this.schedule = schedule;
        this.parentCommentId = parentCommentId;
        this.member = member;
        this.content = content;
    }

    public static CommentEntity of(
            ScheduleEntity schedule,
            MemberEntity member,
            String content
    ) {
        return new CommentEntity(schedule, member, null, content);
    }

    public void addChildComment(CommentEntity child) {
        child.setParentCommentId(this.id);
        this.getChildComment().add(child);
    }
}
