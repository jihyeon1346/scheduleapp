package com.example.scheduleapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @Size(min = 8, message = "비밀번호는{min}자 이상어야 합니다.")
    private String password;
}
