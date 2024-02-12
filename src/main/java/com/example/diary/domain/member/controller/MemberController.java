package com.example.diary.domain.member.controller;

import com.example.diary.domain.member.dto.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.dto.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.dto.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.dto.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.service.MemberServiceImpl;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.global.jwt.JwtProvider;
import com.example.diary.domain.member.util.ValidationChecker;
import com.example.diary.global.web.dto.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    private final JwtProvider jwtProvider;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<String>> signup(
            @Validated @RequestBody MemberCreateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);

        memberService.register(dto);
        return ResponseEntity
                .ok(ResponseDTO.success("정상적으로 회원가입 되었습니다."));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(
            @Validated @RequestBody MemberLoginRequestDTO dto,
            HttpServletResponse response,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        String token = memberService.login(dto);
//        jwtProvider.addTokenToCookie(token, response);
        return ResponseEntity
                .ok(ResponseDTO.success("정상적으로 로그인 되었습니다."));
    }

    @Operation(summary = "회원정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberInfoDTO>> getInfo(
            @RequestAttribute("member") Member member,
            @PathVariable("memberId") Long id
    ) {
        MemberInfoDTO memberInfoDTO = memberService.getById(id, member);
        return ResponseEntity
                .ok(ResponseDTO.success(memberInfoDTO));
    }

    @Operation(summary = "회원정보 수정")
    @PutMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberInfoDTO>> update(
            @PathVariable("memberId") Long id,
            @RequestAttribute("member") Member member,
            @Validated @RequestBody MemberUpdateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        MemberInfoDTO memberInfoDTO = memberService.update(id, dto, member);
        return ResponseEntity
                .ok(ResponseDTO.success(memberInfoDTO));
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<String>> delete(
            @PathVariable("memberId") Long id,
            @RequestAttribute("member") Member member,
            @Validated @RequestBody MemberDeleteRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        memberService.delete(id, dto, member);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 삭제 되었습니다."));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<String>> logout(
            HttpServletRequest request
    ) {
        jwtProvider.expireToken(request);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 로그아웃 되었습니다."));
    }
}
