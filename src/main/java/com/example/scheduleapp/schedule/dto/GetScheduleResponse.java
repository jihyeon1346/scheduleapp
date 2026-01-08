package com.example.scheduleapp.schedule.dto;

import com.example.scheduleapp.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {
    private final Long id;
    private final Long  userId;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public GetScheduleResponse(Long id, Long userId, String userName,String title,  String content,
                               LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
