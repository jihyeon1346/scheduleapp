package com.example.scheduleapp.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {
    private final Long id;
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public GetScheduleResponse(Long id, String name,String title,  String content, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
