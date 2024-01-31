package com.example.diary.domain.schedule.infrastructure;

import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
}
