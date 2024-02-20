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

    public CommentInfoDTO register(CommentCreateRequestDTO dto, Member member) {
        Schedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        CommentCreateDto createDto = new CommentCreateDto(
                member,
                schedule,
                dto.content()
        );

        Comment registered;

        if (dto.parentCommentId() != null) {
            registered = commentRepository
                    .registerSubComment(dto.parentCommentId(), createDto);
        } else {
            registered = commentRepository.register(createDto);
        }

        return CommentInfoDTO.from(registered);
    }

    @Transactional(readOnly = true)
    public CommentInfoDTO findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));
        return CommentInfoDTO.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentInfoDTO> findAllByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId).stream()
                .map(CommentInfoDTO::from)
                .toList();
    }

    public CommentInfoDTO update(Long id, CommentUpdateRequestDTO dto, Member member) {
        Comment originComment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (originComment.isNotOwner(member)) {
            throw new CustomException(ErrorCode.UN_AUTHORIZATION_EXCEPTION);
        }

        return CommentInfoDTO.from(commentRepository.updateById(id, dto.content()));
    }

    public void delete(Long id, Member member) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (comment.isNotOwner(member)) {
            throw new CustomException(ErrorCode.UN_AUTHORIZATION_EXCEPTION);
        }

        commentRepository.deleteById(id);
    }
}
