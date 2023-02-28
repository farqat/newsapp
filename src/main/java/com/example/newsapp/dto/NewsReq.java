package com.example.newsapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewsReq {
    Long id;
    String title;
    String description;
    UploadFileDto file;
}
