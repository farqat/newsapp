package com.example.newsapp.repository;

import com.example.newsapp.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("FROM Token t inner join User u on t.user.id = u.id where u.id = ?1 and t.expired = false or t.revoked = false")
    List<Token> findAllValidTokenByUser(Long userId);


    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.confirmedAt = ?2 ,t.expired = true , t.revoked = true WHERE t.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}