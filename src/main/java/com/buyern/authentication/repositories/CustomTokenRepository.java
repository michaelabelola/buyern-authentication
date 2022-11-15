package com.buyern.authentication.repositories;

import com.buyern.authentication.enums.TokenType;
import com.buyern.authentication.models.CustomToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomTokenRepository extends JpaRepository<CustomToken, Long> {
    Optional<CustomToken> findByUserIdAndTokenAndType(String userId, String token, TokenType type);

    Optional<CustomToken> findByUserIdAndType(String userId, TokenType type);

    long deleteByEmailAndType(String email, TokenType type);

    boolean existsByUserIdAndTokenAndType(String userId, String token, TokenType type);

    @Query("select t from tokens t where t.token = ?1 and t.email = ?2 order by t.token")
    List<CustomToken> findByTokenAndEmailOrderByTokenAsc(String token, String email, Pageable pageable);

    @Query("select t from tokens t where t.userId = ?1 order by t.type")
    List<CustomToken> findByUserIdOrderByTypeAsc(String userId, Pageable pageable);


}