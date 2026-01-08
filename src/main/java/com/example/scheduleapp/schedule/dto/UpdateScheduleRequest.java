package com.example.scheduleapp.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    private String name;
    private String title;
    private String content;
}
