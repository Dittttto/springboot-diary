package com.example.diary.domain.schedule.service;


import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.dto.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleUpdateRequestDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleInfoDTO;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    ScheduleInfoDTO register(ScheduleCreateRequestDTO dto, Member member);

    List<ScheduleInfoDTO> getSchedules();

    ScheduleInfoDTO getScheduleById(Long id);

    ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto, Member member);

    void deleteById(ScheduleDeleteRequestDTO dto, Member member);

    List<ScheduleInfoDTO> searchByTitle(String title);

    Map<Long, List<ScheduleInfoDTO>> findAllByAssignedMember();
}
