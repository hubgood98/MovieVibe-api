package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Movie> searchMovies(String title, boolean adult, Pageable pageable, String voteAverageOrder, String voteCountOrder) {
        Sort sort = Sort.unsorted();

        // 정렬 기준
        if (voteAverageOrder != null && !voteAverageOrder.isBlank()) {
            if ("asc".equalsIgnoreCase(voteAverageOrder)) {
                sort = sort.and(Sort.by(Sort.Direction.ASC, "voteAverage"));
            } else if ("desc".equalsIgnoreCase(voteAverageOrder)) {
                sort = sort.and(Sort.by(Sort.Direction.DESC, "voteAverage"));
            }
        }

        if (voteCountOrder != null && !voteCountOrder.isBlank()) {
            if ("asc".equalsIgnoreCase(voteCountOrder)) {
                sort = sort.and(Sort.by(Sort.Direction.ASC, "voteCount"));
            } else if ("desc".equalsIgnoreCase(voteCountOrder)) {
                sort = sort.and(Sort.by(Sort.Direction.DESC, "voteCount"));
            }
        }

        // PageRequest로 새로운 Pageable 생성
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return movieRepository.findByTitleContainingAndAdult(title, adult, pageable);
    }


    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        return movie.orElseThrow(() -> new NotFoundException("Movie not found"));
    }





}







