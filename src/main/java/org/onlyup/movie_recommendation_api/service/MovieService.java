package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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

    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        return movie.orElseThrow(() -> new NotFoundException("Movie not found"));
    }





}







