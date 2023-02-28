package com.example.newsapp.exception;

public class NotDatabase extends RuntimeException{
    public NotDatabase(String message) {
        super(message);
    }
}
