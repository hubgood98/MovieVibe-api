package org.onlyup.movie_recommendation_api.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId; //유져 ID

    @Column(unique = true, nullable = false)
    private String accountId; //로그인할때 사용하는 아이디

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.DATE) //날짜정보만 저장 (시간은 무시)
    private Date createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;//삭제여부
}
