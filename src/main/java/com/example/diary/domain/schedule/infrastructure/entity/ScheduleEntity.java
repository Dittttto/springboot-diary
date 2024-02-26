package com.example.diary.domain.schedule.infrastructure.entity;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "schedule")
@SQLDelete(sql = "update schedule set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "password")
    private String password;

    @Column(name = "done")
    private Boolean isDone;

    @Column(name = "private")
    private Boolean isPrivate;

    @ManyToOne(optional = true)
    private MemberEntity assignedMember;

    @ManyToOne
    private MemberEntity owner;

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule")
    Set<CommentEntity> commentEntities = new LinkedHashSet<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ScheduleEntity(
            String title,
            String content,
            String password,
            Boolean isDone,
            Boolean isPrivate,
            MemberEntity entity,
            MemberEntity assignedMember
    ) {
        this.title = title;
        this.content = content;
        this.password = password;
        this.owner = entity;
        this.assignedMember = assignedMember;
        this.isDone = isDone;
        this.isPrivate = isPrivate;
    }

    public ScheduleEntity(
            Long id,
            String title,
            String content,
            String password,
            Boolean isDone,
            Boolean isPrivate,
            MemberEntity assignedMember,
            MemberEntity memberEntity,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.password = password;
        this.isDone = isDone;
        this.isPrivate = isPrivate;
        this.assignedMember = assignedMember;
        this.owner = memberEntity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
