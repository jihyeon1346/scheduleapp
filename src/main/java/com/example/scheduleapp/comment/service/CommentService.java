package com.example.scheduleapp.comment.service;

import com.example.scheduleapp.comment.dto.CreateCommentRequest;
import com.example.scheduleapp.comment.dto.CreateCommentResponse;
import com.example.scheduleapp.comment.dto.GetCommentResponse;
import com.example.scheduleapp.comment.entity.Comment;
import com.example.scheduleapp.comment.repository.CommentRepository;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.schedule.repository.ScheduleRepository;
import com.example.scheduleapp.user.dto.SessionUser;
import com.example.scheduleapp.user.entity.User;
import com.example.scheduleapp.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(
            SessionUser sessionUser, Long scheduleId, @Valid CreateCommentRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다")
        );
        Comment comment = new Comment(request.getContent(), schedule, user);
        if(comment.getContent() == null)
        {
            throw new IllegalStateException("댓글을 작성해주세요");
        }
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
                );
    }
    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(SessionUser sessionUser, Long scheduleId) {
        List<Comment> comments = commentRepository.findAll();
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse response = new GetCommentResponse(
                    comment.getId(),
                    comment.getUser().getId(),
                    comment.getSchedule().getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()

            );
            dtos.add(response);
        }
        return dtos;
    }
}
