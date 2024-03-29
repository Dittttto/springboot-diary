package com.example.diary.domain.member.dto.service;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberInfoDTO {
    private final Long id;
    private final String email;
    private final String username;
    private final boolean isAdmin;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MemberInfoDTO from(Member member) {
         return new MemberInfoDTO(
                 member.getId(),
                member.getEmail(),
                member.getUsername(),
                 member.getRole() == MemberRole.ADMIN,
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
