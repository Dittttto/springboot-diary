package com.example.diary.domain.schedule.controller;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.global.web.dto.response.ResponseDTO;
import com.example.diary.domain.schedule.service.ScheduleService;
import com.example.diary.domain.schedule.service.dto.ScheduleInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService service;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ScheduleInfoDTO>>> getSchedules() {
        return ResponseEntity.ok(ResponseDTO.success(service.getSchedules()));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> getSchedule(
            @PathVariable long scheduleId) {
        return ResponseEntity.ok(ResponseDTO.success(service.getScheduleById(scheduleId)));
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createSchedule(
            @RequestBody ScheduleCreateRequestDTO dto,
            @RequestAttribute("member") Member member) {
        service.register(dto, member);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 등록되었습니다."));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> modifySchedule(
            @RequestBody ScheduleUpdateRequestDTO dto,
            @RequestAttribute("member") Member member) {

        return ResponseEntity.ok(ResponseDTO.success(service.modifySchedule(dto, member)));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<String>> deleteSchedule(
            @RequestBody ScheduleDeleteRequestDTO dto,
            @RequestAttribute("member") Member member) {

        service.deleteById(dto, member);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 삭제되었습니다."));
    }
}
