package com.example.scheduleapp.user.service;

import com.example.scheduleapp.user.dto.*;
import com.example.scheduleapp.user.entity.User;
import com.example.scheduleapp.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignupResponse save(SignupRequest request) {
        User user = new User(request.getUserName(), request.getEmail(), request.getPassword());
        if (user.getPassword() == null) {
            throw new IllegalStateException("패스워드를 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new IllegalStateException("패스워드는 8자리 이상이어야 합니다.");
        }
        User savedUser = userRepository.save(user);
        return new SignupResponse(
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
        if (user.getPassword() == null) {
            throw new IllegalStateException("패스워드를 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new IllegalStateException("패스워드는 8자리 이상이어야 합니다.");
        }
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
    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다."));
        if(!user.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return new SessionUser(
                user.getId(),
                user.getEmail(),
                user.getPassword());
    }
}
