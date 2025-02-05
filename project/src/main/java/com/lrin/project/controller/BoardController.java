package com.lrin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
    // 게시글 목록 페이지
    @GetMapping(value = "/board/list")
    public String boardList(Model model) {
        // 모델에 필요한 데이터 추가
        model.addAttribute("cssPath", "board/list");   // CSS 경로 설정
        model.addAttribute("pageTitle", "게시판 목록"); // 페이지 타이틀 설정
        model.addAttribute("jsPath", "/board/board");   // JavaScript 경로 설정
        return "board/list"; // board/list.html (Thymeleaf 템플릿)
    }

    // 게시글 상세보기 페이지
    @GetMapping(value = "/board/detail")
    public String boardDetail(@RequestParam("id") Long id, Model model) {
        // 게시글 상세 조회 로직 (id로 게시글 찾기)
        // 예시: Board board = boardService.getPostById(id);

        model.addAttribute("cssPath", "board/detail");
        model.addAttribute("pageTitle", "게시글 상세보기");
        model.addAttribute("jsPath", "/board/board");
        // 모델에 게시글 데이터 추가 (예시)
        // model.addAttribute("board", board);

        return "board/detail"; // board/detail.html (Thymeleaf 템플릿)
    }

    // 게시글 작성 페이지
    @GetMapping(value = "/board/write")
    public String boardWrite(Model model) {
        // 관리자만 접근할 수 있도록 권한 체크 추가 가능
        model.addAttribute("cssPath", "board/write");
        model.addAttribute("pageTitle", "게시글 작성");
        model.addAttribute("jsPath", "/board/board");
        return "board/write"; // board/write.html (Thymeleaf 템플릿)
    }
}