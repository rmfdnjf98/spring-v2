package com.example.boardv1.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller // 리턴 값 파일명
public class BoardController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/boards/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // ViewResolver
    // prefix : /templates/
    // suffix : .mustache
    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id) {
        return "board/update-form";
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id) {
        return "board/detail";
    }

}
