package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    private Long ratingId;

    private User user;
}
