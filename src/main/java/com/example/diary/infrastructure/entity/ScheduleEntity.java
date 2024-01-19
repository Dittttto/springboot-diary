package com.example.diary.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Builder
@SQLDelete(sql = "update \"schedule\" set deleted_at = NOT() where id = ?")
@Where(clause = "deleted_at is NULL")
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

    @Column(name = "author")
    private String author;

    @Column(name = "password")
    private String password;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deleted_at;

    public static ScheduleEntity of(String title,
                                    String content,
                                    String password,
                                    String author) {

        return ScheduleEntity.builder()
                .title(title)
                .content(content)
                .password(password)
                .author(author)
                .build();
    }
}
