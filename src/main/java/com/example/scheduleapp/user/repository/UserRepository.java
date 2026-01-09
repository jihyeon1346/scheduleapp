package com.example.scheduleapp.user.repository;

import com.example.scheduleapp.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserName(String userName);
    //이메일로 유저 찾기
    Optional<User> findByEmail(String email);
}
