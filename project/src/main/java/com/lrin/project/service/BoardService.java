//package com.lrin.project.service;
//
//import com.lrin.project.model.Board;
//import com.lrin.project.repository.BoardRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BoardService {
//    private final BoardRepository boardRepository;
//
//    public List<Board> getAllPosts() {
//        return boardRepository.findAll();
//    }
//
//    public Board getPostById(Long id) {
//        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
//    }
//
//    public Board createPost(Board board) {
//        return boardRepository.save(board);
//    }
//
//    public Board updatePost(Long id, Board updatedBoard) {
//        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
//        board.setTitle(updatedBoard.getTitle());
//        board.setContent(updatedBoard.getContent());
//        board.setWriter(updatedBoard.getWriter());
//        return boardRepository.save(board);
//    }
//
//    public void deletePost(Long id) {
//        boardRepository.deleteById(id);
//    }
//}
