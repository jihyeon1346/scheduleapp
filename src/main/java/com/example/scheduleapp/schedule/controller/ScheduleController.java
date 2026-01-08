package com.example.scheduleapp.schedule.controller;

import com.example.scheduleapp.schedule.dto.*;
import com.example.scheduleapp.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
