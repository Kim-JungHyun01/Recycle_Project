package com.lrin.project.controller;

import com.lrin.project.entity.board.BoardEntity;
import com.lrin.project.repository.board.BoardRepository;
import com.lrin.project.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 목록 페이지
    @GetMapping(value = "/board/list")
    public String boardList(Model model
                            , @RequestParam(defaultValue = "0") Integer page, // 현재 페이지
                            @RequestParam(defaultValue = "15") Integer size) { // 한 페이지당 보여줄 갯수

        Pageable pageable = PageRequest.of(page, size); // 페이징 객체
        Page<BoardEntity> boardPage = boardRepository.findAll(pageable); // 데이터 조회

        List<BoardEntity> boardEntityList = this.boardRepository.findAll();
        model.addAttribute("boardEntityList", boardEntityList);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        // 모델에 필요한 데이터 추가
        model.addAttribute("cssPath", "board/list");   // CSS 경로 설정
        model.addAttribute("pageTitle", "게시판 목록"); // 페이지 타이틀 설정
        model.addAttribute("jsPath", "/board/board");   // JavaScript 경로 설정
        return "board/list"; // board/list.html (Thymeleaf 템플릿)
    }

    // 게시글 상세보기 페이지
    @GetMapping(value = "/board/detail/{id}")
    public String boardDetail(@PathVariable("id") Long id, Model model) {
        // 게시글 상세 조회 로직 (id로 게시글 찾기)
        BoardEntity board = this.boardService.getListById(id);

        model.addAttribute("cssPath", "board/detail");
        model.addAttribute("pageTitle", "게시글 상세보기");
        model.addAttribute("jsPath", "/board/board");
        // 모델에 게시글 데이터 추가 (예시)
        model.addAttribute("board", board);

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

    // 게시글 추가 기능
    @PostMapping(value = "/board/write")
    public String boardCreate(@RequestParam(value="title") String title, @RequestParam(value="content") String content, @RequestParam(value="writer") String writer) {
        this.boardService.create(title, content, writer);
        return "redirect:/board/list"; // 작성 후 목록으로 이동
    }
}