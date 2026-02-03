package com.example.boardv1.board;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.example.boardv1.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 데이터베이스 세상의 테이블을 자바 세상에 모델링한 결과 = 엔티티
 */

@NoArgsConstructor // 디폴트 생성자
// @Data // getter, setter, toString
@Getter
@Setter
@Entity // 해당 어노테이션을 보고 컴퍼넌트 스캔 후, 데이터베이스 테이블을 생성한다. -> java에서 클래스 만들고 DB 세싱에 만드는 순서
@Table(name = "board_tb") // 테이블명 정하기
public class Board { // user 1, board N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    // private Integer userId;
    @ManyToOne(fetch = FetchType.EAGER) // FK가 된다.
    private User user; // user_id

    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "board : " + id + ", " + title + ", username" + user.getUsername();
    }

}
