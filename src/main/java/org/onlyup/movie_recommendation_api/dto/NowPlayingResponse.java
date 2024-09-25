package org.onlyup.movie_recommendation_api.dto;

import lombok.Getter;
import org.onlyup.movie_recommendation_api.domain.Movie;

import java.util.List;

@Getter
public class NowPlayingResponse {
    private List<Movie> results;

    private void setResults(final List<Movie> results){
        this.results = results;
    }
}
