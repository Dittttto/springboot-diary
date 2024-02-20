package com.example.diary.domain.comment.repository;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.comment.dto.service.CommentCreateDto;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment register(CommentCreateDto dto);
    Comment registerSubComment(Long parentCommentId, CommentCreateDto dto);
    Optional<Comment> findById(Long id);
    List<Comment> findByMemberId(Long id);
    Comment updateById(Long id, String content);
    void deleteById(Long id);
}
