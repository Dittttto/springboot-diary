package com.example.diary.domain.fixture;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.domain.member.model.Member;

public class TestMember extends Member {
    public TestMember() {
        super(
                1L,
                "ditto@gmail.com",
                "qwer1234",
                "ditto",
                MemberRole.DEFAULT,
                null,
                null,
                null
        );
    }

    public TestMember(String email) {
        super(
                1L,
                email,
                "qwer1234",
                "ditto",
                MemberRole.DEFAULT,
                null,
                null,
                null
        );
    }
}
