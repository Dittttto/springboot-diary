package com.example.diary.domain.comment.repository;

import com.example.diary.domain.comment.dto.service.CommentCreateDto;
import com.example.diary.domain.comment.infrastructure.CommentJpaRepository;
import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.comment.model.Comment;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment register(CommentCreateDto dto) {
        CommentEntity commentEntity = CommentEntity.of(
                dto.getSchedule().toEntity(),
                dto.getMember().toEntity(),
                dto.getContent()
        );

        CommentEntity entity = commentJpaRepository.save(commentEntity);
        return Comment.from(entity);
    }

    @Override
    public Comment registerSubComment(Long parentCommentId, CommentCreateDto dto) {
        CommentEntity parentComment = commentJpaRepository
                .getReferenceById(parentCommentId);

        CommentEntity commentEntity = CommentEntity.of(
                dto.getSchedule().toEntity(),
                dto.getMember().toEntity(),
                dto.getContent()
        );

        parentComment.addChildComment(commentEntity);

        return Comment.from(parentComment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJpaRepository.findById(id).map(Comment::from);

    }

    @Override
    public List<Comment> findByMemberId(Long id) {
        return commentJpaRepository
                .findAllByMemberId(id).stream()
                .map(Comment::from)
                .toList();
    }

    @Override
    public Comment updateById(Long id, String content) {
        CommentEntity entity = commentJpaRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        entity.setContent(content);

        commentJpaRepository.saveAndFlush(entity);
        return Comment.from(entity);
    }

    @Override
    public void deleteById(Long id) {
        commentJpaRepository.deleteById(id);
    }
}
