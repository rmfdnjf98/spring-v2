package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

// 책임 : 트랜잭션 관리, DTO만들기, 권한 체크(DB 정보가 필요하니까)  
@RequiredArgsConstructor // -> 생성자 주입
@Service // Ioc 컨테이너에 등록
public class BoardService {

    private final BoardRepository boardRepository; // service 가 의존하는 repository

    public List<Board> 게시글목록() {
        return boardRepository.findAll();
    }

    public BoardResponse.DetailDTO 상세보기(int id, Integer sessionUserId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없어요"));
        return new BoardResponse.DetailDTO(board, sessionUserId);
    }

    public Board 수정폼게시글정보(int id, int sessionUserId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없어요"));

        if (sessionUserId != board.getUser().getId())
            throw new RuntimeException("수정할 권한이 없어요");
        return board;
    }

    @Transactional // update, delete 할 때 붙이기!!!!
    public void 게시물수정(int id, String title, String content, int sessionUserId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 게시글을 찾을 수 없어요"));

        if (sessionUserId != board.getUser().getId())
            throw new RuntimeException("게시글을 수정할 권한이 없어요");

        board.setTitle(title);
        board.setContent(content);
    }

    // 원자성(모든게 다 되면 commit, 하나라도 실패하면 rollback)
    // 트랜잭션 종료시 flush 됨.
    @Transactional
    public void 게시글쓰기(String title, String content) {
        // 1. 비영속 객체
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        System.out.println("before persist" + board.getId());

        // 2. persist
        boardRepository.save(board);

        System.out.println("after persist" + board.getId());
    }

    @Transactional
    public void 게시글삭제(int id, int sessionUserId) {
        Board board = boardRepository.findById(id) // 영속화
                .orElseThrow(() -> new RuntimeException("삭제 할 게시글을 찾을 수 없어요"));

        if (sessionUserId != board.getUser().getId())
            throw new RuntimeException("삭제할 권한이 없어요");

        boardRepository.delete(board);
    }
}
