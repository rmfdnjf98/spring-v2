package com.example.boardv1.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 하이버네이트 기술
 */

@RequiredArgsConstructor // final이 붙어있는 모든 필드를 초기화하는 생성자를 만들어줌.
@Repository // 컴포넌트 스캔해서 new가 자동으로 된다.(싱글톤으로 객체 하나로 관리)
public class BoardRepository {
    private final EntityManager em;

    // DI = 의존성 주입 (의존하고 있는게 IoC에 떠있어야됨)
    // public BoardRepository(EntityManager em) { // 생성자
    // this.em = em;
    // }

    public Optional<Board> findByIdJoinUser(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user where b.id = :id", Board.class);
        query.setParameter("id", id);
        try {
            Board board = (Board) query.getSingleResult();
            return Optional.of(board);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Board> findById(int id) {
        // select * from board_tb where id = 1; 을 찾아라 (캐싱 후 없으면 DB에 쿼리 보내기기)
        // ResultSet rs -> Board 객체 옮기기 (Object Mapping) (답장(rs)오면 board 객체로 만들기 이걸
        // Object Mapping라 한다.)
        // Board board = new Board(); (이 때 새로운 객체 생성)
        // board.id = rs.getInt("id"); (리플렉션은은 private 변수에 접근이 가능 그래서 set 없이 접근 가능)
        Board board = em.find(Board.class, id);
        // System.out.println("board : " + board);
        return Optional.ofNullable(board);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b order by b.id desc", Board.class)
                .getResultList();
    }

    public Board save(Board board) {
        em.persist(board); // 영속화 : 영구히 저장하다.(디스크에 저장)
        return board;
    }

    public void delete(Board board) {
        em.remove(board);
    }

}
