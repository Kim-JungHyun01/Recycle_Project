package com.lrin.project.service.board;

import com.lrin.project.entity.board.BoardEntity;
import com.lrin.project.repository.board.BoardRepository;
import com.lrin.project.service.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 조회
    public BoardEntity getListById(Long id) {
        Optional<BoardEntity> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    // 게시글 추가
    public void create(String title, String content, String writer) {
        BoardEntity board = new BoardEntity();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);
        board.setRegTime(LocalDateTime.now());
        this.boardRepository.save(board); // DB에 저장
    }
}
