package com.example.diary.domain.member.model;


import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

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

    // TODO: update method 가 필요할까? -> 권한 변경이 있을 수 있지 않을까?
    public Member update(
            String username,
            String password,
            boolean isAdmin,
            BCryptPasswordEncoder passwordEncoder
    ) {
        if (isPasswordNotMatch(password, passwordEncoder)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        Member member = new Member();
        member.id = this.id;
        member.email = this.email;
        member.username = username;
        member.password = this.password;
        member.role = isAdmin ? MemberRole.ADMIN : MemberRole.DEFAULT;
        member.createdAt = createdAt;
        member.updatedAt = updatedAt;
        member.deletedAt = deletedAt;

        return member;
    }

    public boolean isPasswordNotMatch(
            final String password,
            final BCryptPasswordEncoder passwordEncoder
    ) {
        return !passwordEncoder.matches(password, this.password);
    }

    public static Member from(final MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public MemberEntity toEntity() {
        return new MemberEntity(
                id,
                email,
                username,
                password,
                role,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return email != null && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public boolean isSameEmail(final String email) {
        return this.email.equals(email);
    }
}
