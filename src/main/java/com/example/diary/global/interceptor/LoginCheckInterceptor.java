package com.example.diary.global.interceptor;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.jwt.JwtProvider;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.CustomJwtException;
import com.example.diary.global.exception.ErrorCode;
import com.example.diary.global.exception.JwtErrorCode;
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
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        if (isSwaggerRequest(request)) {
            return true;
        }

//        String tokenValue = jwtProvider.getTokenFromRequest(request);
        String tokenValue = "";
        if (!StringUtils.hasText(tokenValue) || tokenValue.equals("INVALID")) {
            throw new CustomJwtException(JwtErrorCode.TOKEN_INVALID);
        }

        String token = jwtProvider.substringToken(tokenValue);
//        if (!jwtProvider.validate(token)) {
//            throw new CustomJwtException(JwtErrorCode.TOKEN_INVALID);
//        }

//        Claims memberInfo = jwtProvider.getMemberInfoFromToken(token);
//        Member member = memberRepository.findByEmail(memberInfo.getSubject())
//                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));
//
//        request.setAttribute("member", member);
        return true;
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("swagger")
                || uri.contains("api-docs")
                || uri.contains("webjars");
    }
}
