package com.example.diary.controller;

import com.example.diary.controller.request.ScheduleCreateRequestDTO;
import com.example.diary.controller.request.ScheduleDeleteRequestDTO;
import com.example.diary.controller.request.ScheduleUpdateRequestDTO;
import com.example.diary.controller.response.ResponseDTO;
import com.example.diary.service.ScheduleService;
import com.example.diary.service.dto.ScheduleInfoDTO;
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
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> getSchedule(@PathVariable long scheduleId) {
        return ResponseEntity.ok(ResponseDTO.success(service.getScheduleById(scheduleId)));
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createSchedule(@RequestBody ScheduleCreateRequestDTO dto) {
        service.register(dto);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 등록되었습니다."));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> modifySchedule(@RequestBody ScheduleUpdateRequestDTO dto) {
        return ResponseEntity.ok(ResponseDTO.success(service.modifySchedule(dto)));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<String>> deleteSchedule(@RequestBody ScheduleDeleteRequestDTO dto) {
        service.deleteById(dto);
        return ResponseEntity.ok(ResponseDTO.success("성공적으로 삭제되었습니다."));
    }
}
