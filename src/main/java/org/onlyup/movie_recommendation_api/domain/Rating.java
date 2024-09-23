package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)//데이터가 실제로 필요할 때까지 미리 로드하지 않음(메모리 절약효과)
    @JoinColumn(name = "m_id",nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private Float rating;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
