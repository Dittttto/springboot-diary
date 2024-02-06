package com.example.diary.domain.fixture;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;

import java.util.LinkedHashSet;

public class TestSchedule extends Schedule {
    public TestSchedule() {
        super(
                1L,
                "토이 프로젝트",
                "Spring diary project",
                "qwer1234",
                false,
                false,
                null,
                new TestMember(),
                new LinkedHashSet<>(),
                null,
                null,
                null
        );
    }

    public TestSchedule(Member member, String password) {
        super(
                1L,
                "토이 프로젝트",
                "Spring diary project",
                password,
                false,
                false,
                null,
                member,
                new LinkedHashSet<>(),
                null,
                null,
                null
        );
    }
}
