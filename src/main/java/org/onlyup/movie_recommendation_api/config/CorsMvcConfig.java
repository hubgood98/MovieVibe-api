package org.onlyup.movie_recommendation_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        //front 쪽에서 오는 허용할 요청 포트
        corsRegistry.addMapping("/**") //모든경로에 CORS 설정
                .allowedOrigins("http://localhost:5173", "http://localhost:9200")
                .allowedMethods("*") // 모든 HTTP 메서드 허용
                .allowCredentials(true) // 자격 증명 허용
                .allowedHeaders("*")
                .maxAge(3600L) //최대 캐시 시간
                .exposedHeaders("Authorization"); //노출할 헤더
    }

}
