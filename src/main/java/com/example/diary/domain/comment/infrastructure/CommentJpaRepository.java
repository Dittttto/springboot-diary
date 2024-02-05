package com.example.diary.domain.comment.infrastructure;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByMemberId(Long memberId);
}
