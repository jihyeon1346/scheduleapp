package com.example.scheduleapp.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final Long scheduleId;
    private final Long userId;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public GetCommentResponse(Long id, Long scheduleId, Long userId, String content, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
