package com.example.scheduleapp.user.service;

import com.example.scheduleapp.user.dto.*;
import com.example.scheduleapp.user.entity.User;
import com.example.scheduleapp.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignupResponse save(SignupRequest request) {
        //패스워드 암호화하기
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //암호화한 패스워드 넣기 수정
        User user = new User(request.getUserName(), request.getEmail(), encodedPassword);
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
    //전체조회
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
    //단건조회
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
    //유저업데이트
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        if (request.getPassword() == null) {
            throw new IllegalStateException("패스워드를 입력해주세요.");
        }
        if (request.getPassword().length() < 8) {
            throw new IllegalStateException("패스워드는 8자리 이상이어야 합니다.");
        }
        //암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //암호화한 패스워드로 업데이트
        user.update(request.getUserName(), request.getEmail(), encodedPassword);
        user.update(request.getUserName(), request.getEmail(), request.getPassword());
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
    //유저삭제
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new IllegalStateException("없는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
    //로그인
    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {
        //이메일로 유저찾기
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 이메일입니다."));
        //패스워드비교 equal null 위험 수정
//        if(!user.getPassword().equals(request.getPassword())) {
//            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다.");
//        }
//        if (!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())) {
//            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
//        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return new LoginResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail());
    }
}
