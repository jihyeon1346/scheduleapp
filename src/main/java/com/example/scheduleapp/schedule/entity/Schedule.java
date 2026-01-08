package com.example.scheduleapp.schedule.entity;

import com.example.scheduleapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String content;
    private String title;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;


    public  Schedule(String name, String title, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public void update(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;

    }
}
