package com.example.diary.domain.member.dto.service;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import lombok.Getter;

@Getter
public class MemberCreateDTO {
    private final String username;
    private final String email;
    private final String password;
    private final MemberRole role;

    public MemberCreateDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = MemberRole.DEFAULT;
    }
}
