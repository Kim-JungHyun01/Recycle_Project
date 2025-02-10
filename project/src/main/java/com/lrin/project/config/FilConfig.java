package com.lrin.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}") // application.properties 값 가져오기
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")  // 브라우저에서 접근할 URL 경로
                .addResourceLocations("file:///" + uploadDir + "/");  // 실제 파일 저장 경로
    }
}
