package com.example.boardv1.reply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.boardv1.board.Board;
import com.example.boardv1.board.BoardRepository;
import com.example.boardv1.user.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final EntityManager em;

    @Transactional
    public void 댓글등록(String comment, Integer boardId, Integer sessionUserId) {
        Board board = em.getReference(Board.class, boardId);
        User user = em.getReference(User.class, sessionUserId);
        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setBoard(board);
        reply.setUser(user);

        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(int id, int sessionUserId) {
        // 1. 댓글 찾기
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다"));

        // 2. 권한 체크
        if (sessionUserId != reply.getUser().getId())
            throw new RuntimeException("삭제할 권한이 없어요");

        // 3. 댓글 삭제
        replyRepository.delete(reply);
    }
}
