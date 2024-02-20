package com.example.diary.domain.schedule.repository;

import com.example.diary.domain.schedule.dto.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleUpdateDTO;
import com.example.diary.domain.schedule.infrastructure.ScheduleJpaRepository;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public Schedule register(ScheduleCreateDTO dto) {
        ScheduleEntity entity = scheduleJpaRepository.save(
                new ScheduleEntity(
                        dto.getTitle(),
                        dto.getContent(),
                        dto.getPassword(),
                        dto.getIsDone(),
                        dto.getIsPrivate(),
                        dto.getMember().toEntity(),
                        dto.getAssignedMember() != null
                                ? dto.getAssignedMember().toEntity()
                                : null
                )
        );

        return Schedule.from(entity);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleJpaRepository.findById(id).map(Schedule::from);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleJpaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Schedule::from)
                .toList();
    }

    @Override
    public Schedule update(long id, ScheduleUpdateDTO dto) {
        ScheduleEntity entity = scheduleJpaRepository.findById(id)
                .orElseThrow(()
                        -> new CustomException(
                        ErrorCode.NOT_FOUND_EXCEPTION,
                        String.format("%s는 존재하지 않습니다.", id)
                ));


        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setIsDone(dto.getIsDone());
        entity.setIsPrivate(dto.getIsPrivate());
        entity.setAssignedMember(dto.getAssignedMember().toEntity());

        scheduleJpaRepository.saveAndFlush(entity);
        return Schedule.from(entity);
    }

    @Override
    public void deleteById(long id) {
        scheduleJpaRepository.deleteById(id);
    }

    @Override
    public List<Schedule> searchByTitle(String title) {
        return scheduleJpaRepository.searchByTitleContaining(title)
                .stream()
                .map(Schedule::from)
                .toList();
    }
}
