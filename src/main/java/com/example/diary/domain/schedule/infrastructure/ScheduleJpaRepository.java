package com.example.diary.domain.schedule.infrastructure;

import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByOrderByCreatedAtDesc();
    List<ScheduleEntity> searchByTitleContaining(String title);
}
