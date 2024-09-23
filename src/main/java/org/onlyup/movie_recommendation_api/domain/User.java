package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;

    @Column(unique = true, nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String username;
}
