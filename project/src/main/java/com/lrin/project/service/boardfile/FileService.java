package com.lrin.project.service.boardfile;

import com.lrin.project.entity.boardfile.FileEntity;
import com.lrin.project.repository.boardfile.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    // 파일 저장 디렉토리 application.properties에 설정된 값을 가져온다.
    // file.upload-dir=/uploads/ -> uploadDir 변수에 "/uploads/" 값이 저장
    @Value("${file.upload-dir}")
    private String uploadDir; // 업로드 디렉터리 경로

    @Autowired
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // MultipartFile : 업로드된 파일 처리하는 인터페이스
    // throws IOException : 파일 저장 중 발생하는 입출력 제외
    public FileEntity saveFile(MultipartFile file) throws IOException {

            // 파일 없으면 저장하지 않고 종료
            if (file == null || file.isEmpty()) {
                return null;
            }


            // 파일 이름 생성(UUID로 고유값 생성 = 이름 충돌 방지)
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 디렉토리 생성
            File dir = new File(uploadDir); // 업로드 할 파일 객체 생성
            if (!dir.exists()) { // 폴더 존재 확인
                dir.mkdirs(); // 존재하지 않을 경우 폴더 생성
            }

            // 파일 저장
            Files.copy(file.getInputStream(), filePath);

            // DB에 파일 정보 저장
            FileEntity fileEntity = new FileEntity(
                    originalFileName, // 원본 파일명
                    filePath.toString(), // 저장된 파일 경로
                    file.getContentType(), // 파일 MIME 타입
                    file.getSize() // 파일 크기(bytes)
            );

            return fileRepository.save(fileEntity); // 저장된 파일 경로 반환
        }

}
