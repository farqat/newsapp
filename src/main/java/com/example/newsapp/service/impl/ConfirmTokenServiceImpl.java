package com.example.newsapp.service.impl;

import com.example.newsapp.model.Token;
import com.example.newsapp.repository.TokenRepository;
import com.example.newsapp.service.ConfirmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void saveConfirmToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
