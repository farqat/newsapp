package com.example.newsapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileDto {
    String name;
    Integer size;
    String type;
    byte[] data;
}
