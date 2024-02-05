package com.example.diary.domain.comment.service;

import com.example.diary.domain.comment.controller.dto.CommentCreateRequestDTO;
import com.example.diary.domain.comment.controller.dto.CommentUpdateRequestDTO;
import com.example.diary.domain.comment.service.dto.CommentInfoDTO;
import com.example.diary.domain.member.model.Member;

import java.util.List;

public interface CommentService {
    void register(CommentCreateRequestDTO dto, Member member);
    CommentInfoDTO findById(Long id);
    List<CommentInfoDTO> findByMemberId(Long memberId);
    CommentInfoDTO update(Long id, CommentUpdateRequestDTO dto, Member member);
    void delete(Long id, Member member);
}
