package com.example.diary.domain.schedule.service;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.schedule.dto.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleUpdateRequestDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleInfoDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleUpdateDTO;
import com.example.diary.domain.schedule.model.Schedule;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void register(ScheduleCreateRequestDTO dto, Member member) {
        Member assignedMember = null;

        if (dto.assignedMemberId() != null) {
            assignedMember = memberRepository.findById(dto.assignedMemberId())
                    .orElseThrow(() ->
                            new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));
        }


        ScheduleCreateDTO scheduleCreateDTO = new ScheduleCreateDTO(
                dto.title(),
                dto.content(),
                dto.password(),
                false,
                dto.isPrivate(),
                member,
                assignedMember
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
    public List<ScheduleInfoDTO> searchByTitle(String title) {
        return scheduleRepository.searchByTitle(title)
                .stream().map(ScheduleInfoDTO::from)
                .toList();
    }

    @Override
    public Map<Long, List<ScheduleInfoDTO>> findAllByAssignedMember() {
        Map<Long, List<ScheduleInfoDTO>> map = new HashMap<>();
        scheduleRepository.findAll()
                .forEach(schedule -> {
                    Member assignedMember = schedule.getAssignedMember();
                    Long id;
                    if (assignedMember == null) {
                        id = -1L; // TODO: 어나니머스로 바꿀 예정
                    } else {
                        id = assignedMember.getId();
                    }

                    if (map.containsKey(id)) {
                        map.get(id).add(ScheduleInfoDTO.from(schedule));
                    } else {
                        List<ScheduleInfoDTO> list = new ArrayList<>();
                        list.add(ScheduleInfoDTO.from(schedule));
                        map.put(id, list);
                    }
                });
        return map;
    }

    @Override
    public ScheduleInfoDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        return ScheduleInfoDTO.from(schedule);
    }

    @Override
    @Transactional
    public ScheduleInfoDTO modifySchedule(ScheduleUpdateRequestDTO dto, Member member) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        Member assignedMember = null;

        if (dto.assignedMemberId() != null) {
            assignedMember = memberRepository.findById(dto.assignedMemberId())
                    .orElseThrow(() ->
                            new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));
        }

        Schedule updateSchedule = schedule.update(
                dto.title(),
                dto.content(),
                dto.password(),
                dto.isDone(),
                dto.isPrivate(),
                member,
                assignedMember
        );

        return ScheduleInfoDTO.from(
                scheduleRepository.update(dto.id(),
                        ScheduleUpdateDTO.from(updateSchedule))
        );
    }

    @Override
    @Transactional
    public void deleteById(ScheduleDeleteRequestDTO dto, Member member) {
        Schedule schedule = scheduleRepository.findById(dto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (schedule.hasAuthorization(dto.password(), member)){
            scheduleRepository.deleteById(dto.id());
        }
    }
}
