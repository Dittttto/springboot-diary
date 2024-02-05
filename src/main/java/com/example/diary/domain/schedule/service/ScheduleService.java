package com.example.diary.domain.schedule.service;


import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.domain.schedule.service.dto.ScheduleInfoDTO;

import java.util.List;

public interface ScheduleService {

    void register(ScheduleCreateRequestDTO dto, Member member);

    List<ScheduleInfoDTO> getSchedules();

    ScheduleInfoDTO getScheduleById(Long id);

    ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto, Member member);

    void deleteById(ScheduleDeleteRequestDTO dto, Member member);
}
