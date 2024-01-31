package com.example.diary.domain.member.model;


import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String email;
    private String username;
    private String password;
    private MemberRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // TODO: update method 가 필요할까?
    public Member update(String username,
                         String password,
                         boolean isAdmin) {

        Member member = new Member();
        member.id = this.id;
        member.email = this.email;
        member.username = username;
        member.password = password;
        member.role= isAdmin ? MemberRole.ADMIN : MemberRole.DEFAULT;
        member.createdAt = createdAt;
        member.updatedAt = updatedAt;
        member.deletedAt = deletedAt;

        return member;
    }

    public boolean isPasswordMatch(String password, BCryptPasswordEncoder passwordEncoder) {
        // TODO: 암호화된 것이 필요함
        return passwordEncoder.matches(password, this.password);
    }
}
