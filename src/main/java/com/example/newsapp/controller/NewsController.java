package com.example.newsapp.controller;

import com.example.newsapp.dto.NewsReq;
import com.example.newsapp.dto.NewsRes;
import com.example.newsapp.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Collection<NewsRes> getNews(){
        return newsService.getAllNews();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveNews(@RequestBody NewsReq req){
        newsService.saveNews(req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteNews(@PathVariable Long id){
        newsService.deleteNews(id);
    }
}
