package com.lrin.project.service.board;

import com.lrin.project.entity.board.BoardEntity;
import com.lrin.project.entity.boardfile.FileEntity;
import com.lrin.project.repository.board.BoardRepository;
import com.lrin.project.service.DataNotFoundException;
import com.lrin.project.service.boardfile.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private FileService fileService;

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<BoardEntity> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 게시글 조회
    public BoardEntity getListById(Long id) {
        Optional<BoardEntity> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            // 해당 게시글과 관련된 파일 정보를 Lazy로 로딩
            board.get().getFileEntity();
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    // 게시글 추가
    public void create(String title, String content, String writer, FileEntity fileEntity) {
        BoardEntity board = new BoardEntity();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);
        board.setRegTime(LocalDateTime.now());

        if (fileEntity != null) {
            board.setFileEntity(fileEntity);
        }

        boardRepository.save(board); // DB에 저장
    }

    // 게시글 수정
    public void updateBoard(Long id, String title, String content, MultipartFile file) {
        // 게시글 찾기
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 게시글 제목, 내용 수정
        board.setTitle(title);
        board.setContent(content);
        board.setUpdateTime(LocalDateTime.now());

        // 새 파일 업로드 된 경우
        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제
            if (board.getFileEntity() != null) {
                fileService.deleteFile(board.getFileEntity().getFilePath());
            }

            // 새 파일 저장
            try {
                FileEntity newFile = fileService.saveFile(file);
                board.setFileEntity(newFile);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }

        // 수정된 게시글 저장하기
        boardRepository.save(board);
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


}
