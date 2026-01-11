package com.example.scheduleapp.schedule.service;

import com.example.scheduleapp.schedule.dto.*;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.schedule.repository.ScheduleRepository;
import com.example.scheduleapp.user.dto.SessionUser;
import com.example.scheduleapp.user.entity.User;
import com.example.scheduleapp.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    //일정생성
    @Transactional
    public CreateScheduleResponse save(SessionUser sessionUser, CreateScheduleRequest request) {
        //userId로 User 조회
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new IllegalStateException(("없는 유저입니다"))
        );
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        if(schedule.getTitle() == null)
        {
            throw new IllegalStateException("제목을 입력해주세요.");
        }
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new  CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getUser().getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }
    //전체조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse response = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getUser().getId(),
                    schedule.getUser().getUserName(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt());
            dtos.add(response);
        }
        return dtos;
    }
    //단건조회
    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(SessionUser sessionUser, Long scheduleId) {
        if (sessionUser == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        return new  GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
    //일정업데이트
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request,SessionUser sessionUser) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        if(schedule.getTitle() == null)
        {
            throw new IllegalStateException("제목을 입력해주세요.");
        }
        schedule.update(request.getTitle(), request.getContent());
        return new  UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }
    //일정삭제
    @Transactional
    public void delete(Long scheduleId, SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        boolean existence = scheduleRepository.existsById(scheduleId);
        if(!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
