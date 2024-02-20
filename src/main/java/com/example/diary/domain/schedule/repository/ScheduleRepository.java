package com.example.diary.domain.schedule.repository;

import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.dto.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule register(ScheduleCreateDTO dto);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAll();
    Schedule update(long id, ScheduleUpdateDTO dto);
    void deleteById(long id);
    List<Schedule> searchByTitle(String title);
}
