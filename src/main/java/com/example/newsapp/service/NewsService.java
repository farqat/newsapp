package com.example.newsapp.service;

import com.example.newsapp.dto.NewsReq;
import com.example.newsapp.dto.NewsRes;

import java.util.Collection;

public interface NewsService {
    Collection<NewsRes> getAllNews();
    void saveNews(NewsReq req);
    void deleteNews(Long id);
}
