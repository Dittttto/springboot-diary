package com.example.diary.domain.comment.repository;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.comment.service.dto.CommentCreateDto;

import java.util.List;

public interface CommentRepository {
    void register(CommentCreateDto dto);
    Comment findById(Long id);
    List<Comment> findByMemberId(Long id);
    Comment updateById(Long id, String content);
    void deleteById(Long id);
}
