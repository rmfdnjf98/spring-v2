package com.example.boardv1.board;

import java.io.IOException;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.boardv1.user.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 적혀 있는 상수의 생성자 만들어 준다. -> 생성자 주입
@Controller // 리턴 값 파일명
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // body : title=title7&content=content7 (x-www-form 양식)
    @PostMapping("/boards/save")
    public String save(BoardRequest.SaveOrUpdateDTO reqDTO) throws IOException {
        // 인증(v), 권한(x)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다.");

        boardService.게시글쓰기(reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/";
    }

    @PostMapping("/boards/{id}/update")
    public String update(@PathVariable("id") int id, BoardRequest.SaveOrUpdateDTO reqDTO) {
        // 인증(v), 권한(v)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다.");
        boardService.게시물수정(id, reqDTO.getTitle(), reqDTO.getContent(), sessionUser.getId());
        return "redirect:/boards/" + id;
    }

    @GetMapping("/")
    public String index(HttpServletRequest req) {
        // 인증(x), 권한(x)
        List<Board> list = boardService.게시글목록();
        req.setAttribute("models", list);
        return "index";
    }

    @GetMapping("/boards/save-form")
    public String saveForm() {
        // 인증(v), 권한(x)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다.");

        return "board/save-form";
    }

    // ViewResolver
    // prefix : /templates/
    // suffix : .mustache
    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest req) {
        // 인증(v), 권한(v)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다.");

        Board board = boardService.수정폼게시글정보(id, sessionUser.getId());
        req.setAttribute("model", board);
        return "board/update-form";
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest req) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();
        BoardResponse.DetailDTO dto = boardService.상세보기(id, sessionUserId);
        req.setAttribute("model", dto);
        return "board/detail";
    }

    @PostMapping("/boards/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        // 인증(v), 권한(v)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null)
            throw new RuntimeException("인증이 되지 않았습니다."); // 인증 되지 않으면 오류 발생시켜서 제어
        boardService.게시글삭제(id, sessionUser.getId());
        return "redirect:/";
    }
}
