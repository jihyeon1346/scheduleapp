package com.example.scheduleapp.schedule.service;

import com.example.scheduleapp.schedule.dto.*;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getName(),request.getTitle() ,request.getContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new  CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse response = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getName(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt());
            dtos.add(response);
        }
        return dtos;
    }
    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        return new  GetScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        schedule.update(request.getName(), request.getTitle(), request.getContent());
        return new  UpdateScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }
    @Transactional
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if(!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
