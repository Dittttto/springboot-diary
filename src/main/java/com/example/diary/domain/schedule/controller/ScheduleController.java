package com.example.diary.domain.schedule.controller;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.util.ValidationChecker;
import com.example.diary.domain.schedule.dto.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleUpdateRequestDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleInfoDTO;
import com.example.diary.domain.schedule.service.ScheduleService;
import com.example.diary.global.web.dto.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService service;

    @Operation(summary = "전체 일정 조회")
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ScheduleInfoDTO>>> getSchedules() {
        return ResponseEntity
                .ok(ResponseDTO.success(service.getSchedules()));
    }

    @Operation(summary = "전체 일정 맴버 정렬 조회")
    @GetMapping("/member-group")
    // TODO: 반환 타입 업데이트하기
    public ResponseEntity<ResponseDTO<Map<Long, List<ScheduleInfoDTO>>>> getSchedulesByMember() {
        return ResponseEntity
                .ok(ResponseDTO
                        .success(service.findAllByAssignedMember()));
    }

    @Operation(summary = "일정 조회")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> getSchedule(
            @PathVariable long scheduleId
    ) {
        return ResponseEntity
                .ok(ResponseDTO
                        .success(service.getScheduleById(scheduleId)));
    }


    @Operation(summary = "제목으로 일정 검색")
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<ScheduleInfoDTO>>> searchByTitle(
            @RequestParam("title") String scheduleTitle
    ) {
        return ResponseEntity
                .ok(ResponseDTO
                        .success(service.searchByTitle(scheduleTitle)));
    }

    @Operation(summary = "일정 생성")
    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createSchedule(
            @RequestAttribute("member") Member member,
            @Validated @RequestBody ScheduleCreateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        service.register(dto, member);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 등록되었습니다."));
    }

    @Operation(summary = "일정 수정")
    @PutMapping
    public ResponseEntity<ResponseDTO<ScheduleInfoDTO>> modifySchedule(
            @RequestAttribute("member") Member member,
            @Validated @RequestBody ScheduleUpdateRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        return ResponseEntity
                .ok(ResponseDTO
                        .success(service.modifySchedule(dto, member)));
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping
    public ResponseEntity<ResponseDTO<String>> deleteSchedule(
            @RequestAttribute("member") Member member,
            @Validated @RequestBody ScheduleDeleteRequestDTO dto,
            BindingResult bindingResult
    ) {
        ValidationChecker.hasError(bindingResult);
        service.deleteById(dto, member);
        return ResponseEntity
                .ok(ResponseDTO.success("성공적으로 삭제되었습니다."));
    }
}
