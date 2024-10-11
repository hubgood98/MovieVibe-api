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
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.DATE) //날짜정보만 저장 (시간은 무시)
    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private String role;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted; //삭제여부


    //기본 생성자(JPA에서 필요)
    public User() {
        this.createdAt = new Date();
        this.isDeleted = false;
    }

    public User(String accountId, String password, String username, String email, String role) {
        this();
        this.accountId = accountId;
        this.password = password; // 비밀번호는 암호화 후 설정
        this.username = username;
        this.email = email;
        this.role = role != null ? role:"ROLE_USER";
    }

    //삭제 마킹 메서드
    public void markAsDeleted() {
        this.isDeleted = true;
    }
}
