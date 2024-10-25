package org.onlyup.movie_recommendation_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        //front 쪽에서 오는 허용할 요청 포트
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:9200");
    }

}
