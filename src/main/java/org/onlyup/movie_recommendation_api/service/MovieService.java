package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.client.TmdbClient;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;
    private TmdbClient tmdbClient;


    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    //영화 제목으로 검색
    public Optional<Movie> findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

//    //장르
//    public List<Movie> findByGenre(String genre) {
//        return movieRepository.findByGenre(genre);
//    }

    // 개봉일로 영화 검색
    public List<Movie> findByReleaseDate(Date releaseDate) {
        return movieRepository.findByReleaseDate(releaseDate);
    }

//    // 장르와 개봉일로 영화 검색
//    public List<Movie> findByGenreAndReleaseDate(String genre, Date releaseDate) {
//        return movieRepository.findByGenreAndReleaseDate(genre, releaseDate);
//    }


}







