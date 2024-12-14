package org.onlyup.movie_recommendation_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieRecommendationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendationApiApplication.class, args);

    }

}
