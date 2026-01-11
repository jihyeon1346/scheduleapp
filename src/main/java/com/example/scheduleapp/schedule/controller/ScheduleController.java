package com.example.scheduleapp.schedule.controller;

import com.example.scheduleapp.schedule.dto.*;
import com.example.scheduleapp.schedule.service.ScheduleService;
import com.example.scheduleapp.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //일정생성
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser, request));
    }
    //전체조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(sessionUser));
    }
    //단건조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(sessionUser, scheduleId));
    }
    //일정업데이트
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request, sessionUser));
    }
    //일정삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        scheduleService.delete(scheduleId, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
