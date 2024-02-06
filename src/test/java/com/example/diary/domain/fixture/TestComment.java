package com.example.diary.domain.fixture;

import com.example.diary.domain.comment.model.Comment;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;

import java.util.LinkedHashSet;

public class TestComment extends Comment {
    public TestComment() {
        super(
                1L,
                MemberInfoDTO.from(new TestMember()),
                1L,
                1L,
                new LinkedHashSet<>(),
                "1번 댓글",
                null,
                null,
                null
        );
    }
}
