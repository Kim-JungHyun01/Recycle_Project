package com.lrin.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PythonConfig {
    @Bean
    WebClient webClient(){
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                        .build()).baseUrl("http://localhost:8001/") //파이썬 ai 서버주소
//                업로드한 파일을 ai 서버에 전송하기 위해서 버퍼의 크기를 제한을 제한없이(byteCount : -1) 진행
                .build();
    }//end WebClient

}//end class
