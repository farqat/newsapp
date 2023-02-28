package com.example.newsapp.dto;

import com.example.newsapp.model.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewsRes {
    Long id;
    String image;
    String title;
    String description;
    String author;
    List<Comment> comment;
}
