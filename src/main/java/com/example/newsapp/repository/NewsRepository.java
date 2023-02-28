package com.example.newsapp.repository;

import com.example.newsapp.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}