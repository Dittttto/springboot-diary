package com.example.diary.global.interceptor;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import com.example.diary.global.exception.jwt.CustomJwtException;
import com.example.diary.global.exception.jwt.JwtErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        if (isSwaggerRequest(request)) {
            return true;
        }

        String tokenValue = jwtUtil.getTokenFromRequest(request);
        if (!StringUtils.hasText(tokenValue) || tokenValue.equals("INVALID")) {
            throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        String token = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validate(token)) {
            throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        Claims memberInfo = jwtUtil.getMemberInfoFromToken(token);
        Member member = memberRepository.findByEmail(memberInfo.getSubject())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        request.setAttribute("member", member);
        return true;
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("swagger")
                || uri.contains("api-docs")
                || uri.contains("webjars");
    }
}
