package com.example.scheduleapp.user.service;

import com.example.scheduleapp.user.dto.*;
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
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(request.getUserName(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }
    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUserResponse dto = new GetUserResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt());
            dtos.add(dto);
        }
        return dtos;
    }
    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        user.update(request.getUserName(), request.getEmail(), request.getPassword());
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new IllegalStateException("없는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
}
