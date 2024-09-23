package org.onlyup.movie_recommendation_api.repository;

import org.onlyup.movie_recommendation_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountId(String accountId);

    void deleteByAccountId(String accountId);

    // 논리적으로 삭제되지 않은 사용자만 조회
    List<User> findByisDeletedFalse();
}
