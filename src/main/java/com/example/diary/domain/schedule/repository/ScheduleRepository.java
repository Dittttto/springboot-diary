package com.example.diary.domain.schedule.repository;

import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.service.ScheduleUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void register(ScheduleCreateDTO dto);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAll();
    Schedule update(long id, ScheduleUpdateDTO dto);
    void deleteById(long id);
}
