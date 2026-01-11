package com.example.scheduleapp.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotNull(message = "유저ID를 입력해주세요.")
    private Long userId;
    @NotNull(message = "일정ID를 입력해주세요.")
    private Long scheduleId;
    @NotBlank(message = "댓글을 작성해주세요.")
    private String content;
}
