package com.example.diary.domain.comment.controller;

import com.example.diary.domain.comment.controller.dto.CommentCreateRequestDTO;
import com.example.diary.domain.comment.controller.dto.CommentUpdateRequestDTO;
import com.example.diary.domain.comment.service.CommentService;
import com.example.diary.domain.comment.service.dto.CommentInfoDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.global.web.dto.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<CommentInfoDTO>> getComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(ResponseDTO.success(commentService.findById(commentId)));
    }

    @GetMapping("/wroteComments")
    public ResponseEntity<ResponseDTO<List<CommentInfoDTO>>> getWroteComments(@RequestAttribute("member") Member member) {
        return ResponseEntity.ok(ResponseDTO.success(commentService.findByMemberId(member.getId())));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createComment(
            @RequestBody CommentCreateRequestDTO dto,
            @RequestAttribute("member") Member member
    ) {
        commentService.register(dto, member);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 등록되었습니다."));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<CommentInfoDTO>> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequestDTO dto,
            @RequestAttribute("member") Member member
    ) {
        return ResponseEntity.ok(ResponseDTO.success(commentService.update(commentId, dto, member)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<String>> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestAttribute("member") Member member
    ) {
        commentService.delete(commentId, member);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 삭제되었습니다."));
    }

}
