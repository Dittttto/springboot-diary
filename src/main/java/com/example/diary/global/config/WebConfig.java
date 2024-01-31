package com.example.diary.global.config;

import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.global.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(memberRepository, jwtUtil))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/*",
                        "/*.ico",
                        "/error",
                        "/",
                        "/api/**/users/login",
                        "/api/**/users/signup"
                );
    }
}
