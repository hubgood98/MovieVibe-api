package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @JoinColumn(name = "u_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)//데이터가 실제로 필요할 때까지 미리 로드하지 않음(메모리 절약효과)
    @JoinColumn(name = "m_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int ratingValue;

    @Column(length = 500)
    private String comment;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Rating(User user, Movie movie, int ratingValue, Date createdAt, String comment) {
        this.user = user;
        this.movie = movie;
        this.ratingValue = ratingValue;
        this.createdAt = createdAt;
        this.comment = comment;
    }

    public Rating() {

    }

    public void changeRating(int ratingValue, String comment) {
        this.ratingValue = ratingValue;
        this.comment = comment;
    }
}
