package com.example.diary.service;

import com.example.diary.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.exception.CustomException;
import com.example.diary.exception.ErrorCode;
import com.example.diary.model.Schedule;
import com.example.diary.repository.ScheduleRepository;
import com.example.diary.service.dto.ScheduleCreateDTO;
import com.example.diary.service.dto.ScheduleInfoDTO;
import com.example.diary.service.dto.ScheduleUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

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
                .orElseThrow(throwNotFoundException(id));

        return ScheduleInfoDTO.from(schedule);
    }

    @Override
    @Transactional
    public ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(throwNotFoundException(dto.id()));

        Schedule updateSchedule = schedule.update(
                dto.title(),
                dto.content(),
                dto.author(),
                dto.password()
        );

        return ScheduleInfoDTO.from(scheduleRepository.update(dto.id(),
                ScheduleUpdateDTO.from(updateSchedule)));
    }

    @Override
    @Transactional
    public void deleteById(ScheduleDeleteRequestDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(throwNotFoundException(dto.id()));

        schedule.delete(dto.password());

        scheduleRepository.deleteById(dto.id());
    }

    private static Supplier<CustomException> throwNotFoundException(Long dto) {
        return () -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION,
                String.format("%s는 존재하지 않습니다.", dto));
    }
}
