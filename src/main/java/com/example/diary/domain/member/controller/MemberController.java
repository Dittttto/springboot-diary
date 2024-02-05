package com.example.diary.domain.member.controller;

import com.example.diary.domain.member.controller.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.controller.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.controller.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.controller.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.service.MemberServiceImpl;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.domain.member.util.ValidationChecker;
import com.example.diary.global.web.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<?>> signup(
            @Validated @RequestBody MemberCreateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);

        memberService.register(dto);
        return ResponseEntity.ok(ResponseDTO.success("정상적으로 회원가입 되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(
            @Validated @RequestBody MemberLoginRequestDTO dto,
            HttpServletResponse response,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);

        String token = memberService.login(dto);
        jwtUtil.addTokenToCookie(token, response);

        // TODO 메시지를 중앙화 해야하지 않을까?
        return ResponseEntity.ok(ResponseDTO.success("정상적으로 로그인 되었습니다."));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberInfoDTO>> getInfo(
            @PathVariable("memberId") Long id
    ) {
        MemberInfoDTO memberInfoDTO = memberService.getById(id);
        return ResponseEntity.ok(ResponseDTO.success(memberInfoDTO));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberInfoDTO>> update(
            @Validated @RequestBody MemberUpdateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        MemberInfoDTO memberInfoDTO = memberService.update(dto);
        return ResponseEntity.ok(ResponseDTO.success(memberInfoDTO));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<String>> delete(
            @Validated @RequestBody MemberDeleteRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        memberService.delete(dto);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 삭제 되었습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<String>> logout(
            HttpServletRequest request
    ) {
        jwtUtil.expiredToken(request);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 로그아웃 되었습니다."));
    }
}
