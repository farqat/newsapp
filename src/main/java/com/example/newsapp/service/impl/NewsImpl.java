package com.example.newsapp.service.impl;

import com.example.newsapp.dto.NewsReq;
import com.example.newsapp.dto.NewsRes;
import com.example.newsapp.dto.UploadFileDto;
import com.example.newsapp.exception.NotDatabase;
import com.example.newsapp.model.News;
import com.example.newsapp.model.UploadFile;
import com.example.newsapp.model.User;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.repository.UploadFileRepository;
import com.example.newsapp.service.NewsService;
import com.example.newsapp.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsImpl implements NewsService {

    private final NewsRepository repository;
    private final UploadFileRepository uploadFileRepository;

    @Override
    public Collection<NewsRes> getAllNews() {
        return repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void saveNews(NewsReq req) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (req.getId() != null) {
            News news = repository.findById(req.getId()).orElseThrow(() -> new NotDatabase("not found"));
            news.setTitle(req.getTitle());
            news.setDescription(req.getDescription());
            news.setUser(user);
            if (req.getFile() != null) {
                if (news.getUploadFile() != null) {
                    uploadFileRepository.deleteById(news.getUploadFile().getId());
                }
                UploadFile uploadFile = setFile(req.getFile());
                news.setUploadFile(uploadFile);
            }
            repository.save(news);
            log.info("Updated news with ID {}", req.getId());
        } else {
            News news = News.builder()
                    .title(req.getTitle())
                    .description(req.getDescription())
                    .user(user)
                    .uploadFile(req.getFile() != null ? setFile(req.getFile()) : null)
                    .build();

            repository.save(news);
            log.info("Saved new News with ID {}", news.getId());
        }
    }

    private UploadFile setFile(UploadFileDto file) {
        byte[] data = ImageUtil.compressImage(file.getData());
        return UploadFile.builder()
                .data(data)
                .fileName(file.getName())
                .fileType(file.getType())
                .originalFileSize(file.getSize())
                .compressedFileSize(data.length)
                .build();
    }

    @Override
    public void deleteNews(Long id) {
        repository.deleteById(id);
    }

    private NewsRes mapToDto(News n) {
        String image = null;
        if (n.getUploadFile() != null) {
            byte[] data = ImageUtil.decompressImage(n.getUploadFile().getData());
            String encode64 = Base64.getEncoder().encodeToString(data);
            image = "data:image/" + n.getUploadFile().getFileType() + ";base64," + encode64;
        }
        return NewsRes.builder()
                .id(n.getId())
                .image(image)
                .title(n.getTitle())
                .description(n.getDescription())
                .author(n.getUser().getFirstName() + n.getUser().getLastName())
                .comment(n.getComment())
                .build();
    }
}
