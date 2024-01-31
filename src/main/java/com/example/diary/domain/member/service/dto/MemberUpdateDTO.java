package com.example.diary.domain.member.service.dto;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import lombok.Getter;

@Getter
public class MemberUpdateDTO {
    private final String username;
    private final String password;
    private final MemberRole role;

    public MemberUpdateDTO(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.role = isAdmin ? MemberRole.ADMIN : MemberRole.DEFAULT;
    }
}
