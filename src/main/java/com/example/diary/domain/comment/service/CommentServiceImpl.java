package com.example.diary.domain.comment.service;

import com.example.diary.domain.comment.dto.request.CommentCreateRequestDTO;
import com.example.diary.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.example.diary.domain.comment.dto.service.CommentCreateDto;
import com.example.diary.domain.comment.dto.service.CommentInfoDTO;
import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.comment.repository.CommentRepository;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public void register(CommentCreateRequestDTO dto, Member member) {
        Schedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        CommentCreateDto createDto = new CommentCreateDto(
                member,
                schedule,
                dto.content()
        );

        if (dto.parentCommentId() != null) {
            commentRepository.registerSubComment(dto.parentCommentId(), createDto);
        } else {
            commentRepository.register(createDto);
        }
    }

    @Transactional(readOnly = true)
    public CommentInfoDTO findById(Long id) {
        Comment comment = commentRepository.findById(id);
        return CommentInfoDTO.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentInfoDTO> findByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId).stream()
                .map(CommentInfoDTO::from)
                .toList();
    }

    public CommentInfoDTO update(Long id, CommentUpdateRequestDTO dto, Member member) {
        Comment originComment = commentRepository.findById(id);

        if (originComment.isNotOwner(member)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return CommentInfoDTO.from(commentRepository.updateById(id, dto.content()));
    }

    public void delete(Long id, Member member) {
        Comment comment = commentRepository.findById(id);

        if (comment.isNotOwner(member)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        commentRepository.deleteById(id);
    }
}
