package com.example.scheduleapp.schedule.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @Size(min = 1, max = 20, message = "제목은{min} ~ {max}자 이내로 입력해주세요.")
    private String title;
    private String content;
}
