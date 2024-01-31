package com.example.diary.domain.member.controller.request;

public record MemberUpdateRequestDTO(Long id,
                                     String username,
                                     String password,
                                     Boolean isAdmin) {
}
