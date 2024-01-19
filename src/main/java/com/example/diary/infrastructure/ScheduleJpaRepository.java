package com.example.diary.infrastructure;

import com.example.diary.infrastructure.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
}
