package com.example.diary.domain.mock;

import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import com.example.diary.domain.schedule.dto.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleUpdateDTO;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.repository.ScheduleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeScheduleRepository implements ScheduleRepository {
    private Long autoGeneratedId = 0L;
    private final Map<Long, ScheduleEntity> store = new HashMap<>();

    @Override
    public Schedule register(ScheduleCreateDTO dto) {
        ScheduleEntity entity = new ScheduleEntity(
                dto.getTitle(),
                dto.getContent(),
                dto.getPassword(),
                dto.getIsDone(),
                dto.getIsPrivate(),
                dto.getMember().toEntity(),
                dto.getAssignedMember() != null
                        ? dto.getAssignedMember().toEntity()
                        : null
        );

        store.put(++autoGeneratedId, entity);
        return Schedule.from(entity);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        if (store.get(id) == null) {
            return Optional.empty();
        }

        return Optional.of(
                Schedule.from(store.get(id))
        );
    }

    @Override
    public List<Schedule> findAll() {
        return store.values().stream()
                .map(Schedule::from)
                .toList();
    }

    @Override
    public Schedule update(long id, ScheduleUpdateDTO dto) {
        ScheduleEntity entity = store.get(id);
        MemberEntity assignedMember = dto.getAssignedMember() == null
                ? null
                : dto.getAssignedMember().toEntity();

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setPassword(dto.getPassword());
        entity.setIsDone(dto.getIsDone());
        entity.setIsPrivate(dto.getIsPrivate());
        entity.setAssignedMember(assignedMember);
        return Schedule.from(entity);
    }

    @Override
    public void deleteById(long id) {
        store.remove(id);
    }

    @Override
    public List<Schedule> searchByTitle(String title) {
        return store.values().stream()
                .filter(entity -> entity.getTitle().contains(title))
                .map(Schedule::from)
                .toList();
    }
}