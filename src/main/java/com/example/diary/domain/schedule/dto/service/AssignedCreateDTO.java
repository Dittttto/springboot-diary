package com.example.diary.domain.schedule.dto.service;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;

public record AssignedCreateDTO(
        Member member,
        Schedule schedule
) {
}
