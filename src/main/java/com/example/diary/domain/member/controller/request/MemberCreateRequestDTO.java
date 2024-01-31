package com.example.diary.domain.member.controller.request;

public record MemberCreateRequestDTO(String email, String username,
                                     String password) {
}
