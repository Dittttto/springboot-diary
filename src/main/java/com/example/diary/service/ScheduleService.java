package com.example.diary.service;

import com.example.diary.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.service.dto.ScheduleInfoDTO;

import java.util.List;

public interface ScheduleService {

    void register(ScheduleCreateRequestDTO dto);

    List<ScheduleInfoDTO> getSchedules();

    ScheduleInfoDTO getScheduleById(Long id);

    ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto);

    void deleteById(ScheduleDeleteRequestDTO dto);
}
