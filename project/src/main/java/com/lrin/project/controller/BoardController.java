package com.lrin.project.controller;

import com.lrin.project.entity.board.BoardEntity;
import com.lrin.project.entity.boardfile.FileEntity;
import com.lrin.project.repository.board.BoardRepository;
import com.lrin.project.service.board.BoardService;
//import com.lrin.project.service.boardfile.FileService;
import com.lrin.project.service.boardfile.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    @Autowired
    private FileService fileService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 목록 페이지
    @GetMapping(value = "/board/list")
    public String boardList(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 15; // 한 페이지당 보여줄 갯수
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id"))); // id 기준 내림차순 정렬
        Page<BoardEntity> boardPage = boardService.getBoardList(pageable);

        List<BoardEntity> boardEntityList = this.boardRepository.findAll();
        model.addAttribute("boardEntityList", boardEntityList);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        // 모델에 필요한 데이터 추가
        model.addAttribute("cssPath", "board/list");   // CSS 경로 설정
        model.addAttribute("pageTitle", "게시판 목록"); // 페이지 타이틀 설정
        model.addAttribute("jsPath", "board/board");   // JavaScript 경로 설정
        return "board/list"; // board/list.html (Thymeleaf 템플릿)
    }

    // 게시글 상세보기 페이지
    @GetMapping(value = "/board/detail/{id}")
    public String boardDetail(@PathVariable("id") Long id, Model model) {
        // 게시글 상세 조회 로직 (id로 게시글 찾기)
        BoardEntity board = this.boardService.getListById(id);

        model.addAttribute("cssPath", "board/detail");
        model.addAttribute("pageTitle", "게시글 상세보기");
        model.addAttribute("jsPath", "board/board");
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
        model.addAttribute("jsPath", "board/board");
        return "board/write"; // board/write.html (Thymeleaf 템플릿)
    }

    // 게시글 추가 기능
    @PostMapping(value = "/board/write")
    public String boardCreate(@RequestParam("file") MultipartFile file, @RequestParam(value="title") String title, @RequestParam(value="content") String content, @RequestParam(value="writer") String writer, Model model) {

        try {
            // 파일 저장
            FileEntity savedFile = fileService.saveFile(file);

            String filePath = (savedFile != null) ? savedFile.getFilePath() : "첨부 파일 없음";

            // 파일과 함께 게시글 생성
            boardService.create(title, content, writer, savedFile);  // 파일 엔티티와 함께 게시글 생성

            model.addAttribute("message", "게시글 저장 성공! " + "파일 경로: " + filePath);
        } catch (IOException e) {
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
        }

        return "redirect:/board/list"; // 작성 후 목록으로 이동
    }

    // 게시글 수정 페이지
    @GetMapping(value = "/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Model model) {
        // 게시글 정보 가져오기
        BoardEntity board = this.boardService.getListById(id);
        model.addAttribute("board", board);

        model.addAttribute("cssPath", "board/update");
        model.addAttribute("pageTitle", "게시글 수정");
        model.addAttribute("jsPath", "board/board");

        return "board/update"; // board/update.html (수정 페이지)
    }

    // 게시글 수정 기능
    @PostMapping("/board/update/{id}")
    public String boardUpdateSubmit(@PathVariable("id") Long id,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content) {
        boardService.updateBoard(id, title, content);
        return "redirect:/board/detail/" + id; // 수정 후 상세 페이지로 이동
    }

    // 게시글 삭제 기능
    @PostMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board/list"; //
    }

}