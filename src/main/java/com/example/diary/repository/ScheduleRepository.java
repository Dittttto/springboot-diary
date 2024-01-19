package com.example.diary.repository;

import com.example.diary.model.Schedule;
import com.example.diary.service.dto.ScheduleCreateDTO;
import com.example.diary.service.dto.ScheduleUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void register(ScheduleCreateDTO dto);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAll();
    Schedule update(long id, ScheduleUpdateDTO dto);
    void deleteById(long id);
}
