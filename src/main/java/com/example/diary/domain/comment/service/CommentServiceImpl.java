package com.example.diary.domain.comment.service;

import com.example.diary.domain.comment.controller.dto.CommentCreateRequestDTO;
import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.comment.repository.CommentRepository;
import com.example.diary.domain.comment.service.dto.CommentCreateDto;
import com.example.diary.domain.comment.service.dto.CommentInfoDTO;
import com.example.diary.domain.comment.controller.dto.CommentUpdateRequestDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        commentRepository.register(createDto);
    }

    public CommentInfoDTO findById(Long id) {
        Comment comment = commentRepository.findById(id);
        return CommentInfoDTO.from(comment);
    }

    public List<CommentInfoDTO> findByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId).stream()
                .map(CommentInfoDTO::from)
                .toList();
    }

    public CommentInfoDTO update(Long id, CommentUpdateRequestDTO dto, Member member) {
        Comment originComment = commentRepository.findById(id);
        Comment updateComment = originComment.update(member, dto.content());
        return CommentInfoDTO.from(commentRepository.updateById(id, updateComment.getContent()));
    }

    public void delete(Long id, Member member) {
        Comment comment = commentRepository.findById(id);
        comment.delete(member); // TODO: 흐름이 부자연스럽다
        commentRepository.deleteById(id);
    }
}
