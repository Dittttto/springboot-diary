package com.example.diary.domain.comment.controller;

import com.example.diary.domain.comment.dto.request.CommentCreateRequestDTO;
import com.example.diary.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.example.diary.domain.comment.dto.service.CommentInfoDTO;
import com.example.diary.domain.comment.service.CommentService;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.util.ValidationChecker;
import com.example.diary.global.web.dto.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comments", description = "댓글")
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<CommentInfoDTO>> getComment(
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity
                .ok(ResponseDTO
                        .success(commentService.findById(commentId)));
    }

    @Operation(summary = "내가 쓴 댓글 조회")
    @GetMapping("/wroteComments")
    public ResponseEntity<ResponseDTO<List<CommentInfoDTO>>> getWroteComments(
            @RequestAttribute("member") Member member
    ) {
        return ResponseEntity
                .ok(ResponseDTO
                        .success(commentService.findByMemberId(member.getId())));
    }

    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createComment(
            @RequestAttribute("member") Member member,
            @Validated @RequestBody CommentCreateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        commentService.register(dto, member);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 등록되었습니다."));
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<CommentInfoDTO>> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestAttribute("member") Member member,
            @Validated @RequestBody CommentUpdateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        return ResponseEntity
                .ok(ResponseDTO
                        .success(commentService.update(commentId, dto, member)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<String>> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestAttribute("member") Member member
    ) {
        commentService.delete(commentId, member);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 삭제되었습니다."));
    }
}
