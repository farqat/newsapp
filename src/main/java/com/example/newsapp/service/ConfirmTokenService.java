package com.example.newsapp.service;

import com.example.newsapp.model.Token;

import java.util.Optional;

public interface ConfirmTokenService {
    void saveConfirmToken(Token token);
    Optional<Token> getToken(String token);
    int setConfirmedAt(String token);
}
