package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // TMDb에서의 영화 ID

    @Column(nullable = false)
    private String title; // 영화 제목

    @Column(name = "original_title", nullable = false)
    private String originalTitle; // 원제

    @Column(columnDefinition = "TEXT")
    private String overview; // 영화 설명

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate; // 개봉일

    @Column(name = "original_language")
    private String originalLanguage; // 원어

    private boolean adult; // 성인 영화 여부

    private double popularity; // 인기 점수

    @Column(name = "vote_count")
    private int voteCount; // 투표 수

    @Column(name = "vote_average")
    private double voteAverage; // 평균 평점

    @ElementCollection
    @Column(name = "genre_ids")
    private List<Integer> genreIds; // 장르 ID 리스트

    @Column(name = "poster_path")
    private String posterPath; // 포스터 경로

    @Column(name = "backdrop_path")
    private String backdropPath; // 배경 이미지 경로
}
