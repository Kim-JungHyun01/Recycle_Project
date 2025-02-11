package com.lrin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.ErrorManager;

@RestController
public class PythonController {

    @Autowired
    private WebClient webClient;

    @PostMapping("/image_service")
    public String imageRequest(MultipartFile file){
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource()); //폼데이터, 파일
        String result = webClient.post()
                .uri("/image_service")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result;
    }//end imageRequest -> http://localhost:8080/java_service 요청 post처리

    @PostMapping("/video_service")
    public String videoRequest(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return createErrorResponse("파일이 비어있습니다.");
        }

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource()); // 폼데이터, 파일

        try {
            String result = webClient.post()
                    .uri("/video_service")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), clientResponse -> {
//                        logger.error("클라이언트 오류: {}", clientResponse.statusCode());
                        return Mono.error(new RuntimeException("클라이언트 오류: " + clientResponse.statusCode()));
                    })
                    .onStatus(status -> status.is5xxServerError(), clientResponse -> {
//                        logger.error("서버 오류: {}", clientResponse.statusCode());
                        return Mono.error(new RuntimeException("서버 오류: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(String.class)
                    .block();
            return result;
        } catch (Exception e) {
//            logger.error("서버 요청 중 오류 발생", e);
            return createErrorResponse("서버 요청 중 오류 발생: " + e.getMessage());
        }
    }

    private String createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        try {
            return new ObjectMapper().writeValueAsString(errorResponse); // JSON으로 변환
        } catch (Exception jsonException) {
            return "{\"error\": \"JSON 변환 중 오류 발생\"}"; // JSON 변환 실패 시 기본 오류 메시지
        }
    }



}//end class
