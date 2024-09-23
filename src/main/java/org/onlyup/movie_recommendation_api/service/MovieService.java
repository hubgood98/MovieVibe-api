package org.onlyup.movie_recommendation_api.service;

import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    //TMDB를 사용하여 Movie 정보를 가져오기
    @Autowired
    private MovieRepository movieRepository;
}
