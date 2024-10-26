package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.client.TmdbClient;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    //영화 제목으로 검색
    public Page<Movie> searchMovies(String title,boolean adult, Pageable pageable) {

        return movieRepository.findByTitleContainingAndAdult(title, adult, pageable);
    }





}







