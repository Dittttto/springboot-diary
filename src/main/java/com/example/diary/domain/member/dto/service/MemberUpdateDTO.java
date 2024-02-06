package com.example.diary.domain.member.dto.service;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.domain.member.model.Member;
import lombok.Getter;

@Getter
public class MemberUpdateDTO {
    private final String username;
    private final String password;
    private final MemberRole role;

    public MemberUpdateDTO(String username, String password, MemberRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static MemberUpdateDTO from(Member member) {
        return new MemberUpdateDTO(
                member.getUsername(),
                member.getPassword(),
                member.getRole()
        );
    }
}
