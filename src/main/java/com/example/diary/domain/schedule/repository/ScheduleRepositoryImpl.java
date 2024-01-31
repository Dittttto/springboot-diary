package com.example.diary.domain.schedule.repository;

import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import com.example.diary.domain.schedule.infrastructure.ScheduleJpaRepository;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.service.ScheduleUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public void register(ScheduleCreateDTO dto) {
        scheduleJpaRepository.save(ScheduleEntity.of(
                dto.getTitle(),
                dto.getContent(),
                dto.getPassword(),
                dto.getAuthor()
        ));
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleJpaRepository.findById(id).map(ScheduleEntity::toModel);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleJpaRepository.findAll()
                .stream()
                .map(ScheduleEntity::toModel)
                .toList();
    }

    @Override
    public Schedule update(long id, ScheduleUpdateDTO dto) {
        ScheduleEntity entity = scheduleJpaRepository.findById(id)
                .orElseThrow(()
                        -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION, String.format("%s는 존재하지 않습니다.", id)));
        

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthor(dto.getAuthor());

        scheduleJpaRepository.saveAndFlush(entity);
        return entity.toModel();
    }

    @Override
    public void deleteById(long id) {
        scheduleJpaRepository.deleteById(id);
    }
}
