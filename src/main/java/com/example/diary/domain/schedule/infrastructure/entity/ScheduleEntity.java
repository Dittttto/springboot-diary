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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@Builder
@SQLDelete(sql = "update schedule set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "schedule")
    List<CommentEntity> commentEntities = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public ScheduleEntity(
            String title,
            String content,
            String password,
            MemberEntity entity
    ) {
        this.title = title;
        this.content = content;
        this.password = password;
        this.memberEntity = entity;
    }

    public ScheduleEntity(Long id,
                          String title,
                          String content,
                          String password,
                          MemberEntity memberEntity,
                          LocalDate createdAt,
                          LocalDate updatedAt,
                          LocalDate deletedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.password = password;
        this.memberEntity = memberEntity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
