package com.example.diary.domain.schedule.service;

import com.example.diary.domain.schedule.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Override
    @Transactional
    public void register(ScheduleCreateRequestDTO dto) {
        ScheduleCreateDTO scheduleCreateDTO = new ScheduleCreateDTO(
                dto.title(),
                dto.content(),
                dto.author(),
                dto.password()
        );

        scheduleRepository.register(scheduleCreateDTO);
    }

    @Override
    public List<ScheduleInfoDTO> getSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleInfoDTO::from)
                .toList();
    }

    @Override
    public ScheduleInfoDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        return ScheduleInfoDTO.from(schedule);
    }

    @Override
    @Transactional
    public ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        Schedule updateSchedule = schedule.update(
                dto.title(),
                dto.content(),
                dto.author(),
                dto.password()
        );

        return ScheduleInfoDTO.from(scheduleRepository.update(dto.id(), ScheduleUpdateDTO.from(updateSchedule)));
    }

    @Override
    @Transactional
    public void deleteById(ScheduleDeleteRequestDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        schedule.deleteSchedule(dto.password());
        scheduleRepository.deleteById(dto.id());
    }
}
