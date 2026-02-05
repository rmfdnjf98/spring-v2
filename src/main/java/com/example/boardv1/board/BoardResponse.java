package com.example.boardv1.board;

import java.util.List;

import com.example.boardv1.reply.ReplyRespons;

import lombok.Data;

public class BoardResponse {

    @Data
    public static class DetailDTO {

        // 화면에 보이지 않는 것
        private int id;
        private int userId;

        // 화면에 보이는 것
        private String title;
        private String content;
        private String username;

        // 연산해서 만들어야 되는 것
        private boolean isOwner; // 게시글의 주인인가??

        private List<ReplyRespons.DTO> replies;

        public DetailDTO(Board board, Integer sessionUserId) {
            this.id = board.getId();
            this.userId = board.getUser().getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();
            this.isOwner = board.getUser().getId() == sessionUserId; // 로그인 유저 id 와 게시글 작성 id 가 같은지 확인
            this.replies = board.getReplies().stream()
                    .map(reply -> new ReplyRespons.DTO(reply, sessionUserId))
                    .toList();
        }
    }
}
