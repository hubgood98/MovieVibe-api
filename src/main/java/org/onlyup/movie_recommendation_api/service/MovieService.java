package org.onlyup.movie_recommendation_api.service;

import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    //TMDB를 사용하여 Movie 정보를 가져오기
    @Autowired
    private MovieRepository movieRepository;

    @Value("${movie-recommendation-api.api.key")
    private String apiKey;

    private final String API_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY_PARAM = apiKey;//나의 비밀 api 키
}
