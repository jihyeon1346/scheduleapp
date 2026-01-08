package com.example.scheduleapp.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String userName;
    private String email;
    private String password;
}
