package org.onlyup.movie_recommendation_api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    @Column(length = 2000)
    private String overview;

    @JsonProperty("release_date")
    private Date releaseDate;

    @Setter
    @JsonProperty("poster_path")
    private String posterPath;

    @Setter
    private String w500PosterPath; // w500 사이즈 포스터 경로

    @JsonProperty("backdrop_path")
    private String backdropPath;

    private Double popularity;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @ElementCollection
    @JsonProperty("genre_ids")
    private List<Integer> genreIds; // 장르 ID 목록

    private Boolean adult;

    private Boolean video;
}
