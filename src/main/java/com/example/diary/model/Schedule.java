package com.example.diary.model;

import com.example.diary.exception.CustomException;
import com.example.diary.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public Schedule update(String title, String content, String author, String password) {
        if (!this.password.equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return Schedule.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .password(password)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public void delete(String password) {
        if (!this.password.equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }
    }
}
