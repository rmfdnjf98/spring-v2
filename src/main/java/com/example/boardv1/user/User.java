package com.example.boardv1.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // object mapping을 hibernate가 할 때 디폴트 생성자를 new를 한다.
@Data
@Entity // Entity가 붙어있어야 pc가 접근 할 수 있다.
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true) // pk, uk 일 때 인덱스를 만들어준다 (15% 이하는 인덱스화 시키는게 유리)
    private String username;
    @Column(nullable = false, length = 100) // 절대 null이 올 수 없다, 길이 제한
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
