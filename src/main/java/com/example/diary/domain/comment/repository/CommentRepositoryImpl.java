package com.example.diary.domain.comment.repository;

import com.example.diary.domain.comment.infrastructure.CommentJpaRepository;
import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.comment.service.dto.CommentCreateDto;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public void register(CommentCreateDto dto) {
        CommentEntity commentEntity = CommentEntity.of(
                dto.getSchedule().toEntity(),
                dto.getMember().toEntity(),
                dto.getContent()
        );

        commentJpaRepository.save(commentEntity);
    }

    @Override
    public void registerSubComment(Long parentCommentId, CommentCreateDto dto) {
        CommentEntity parentComment = commentJpaRepository.getReferenceById(parentCommentId);

        CommentEntity commentEntity = CommentEntity.of(
                dto.getSchedule().toEntity(),
                dto.getMember().toEntity(),
                dto.getContent()
        );

        parentComment.addChildComment(commentEntity);
    }

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository
                .findById(id)
                .map(Comment::from)
                .orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

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
