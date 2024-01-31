package com.example.diary.domain.schedule.service;


import com.example.diary.domain.schedule.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleUpdateRequestDTO;

import java.util.List;

public interface ScheduleService {

    void register(ScheduleCreateRequestDTO dto);

    List<ScheduleInfoDTO> getSchedules();

    ScheduleInfoDTO getScheduleById(Long id);

    ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto);

    void deleteById(ScheduleDeleteRequestDTO dto);
}
