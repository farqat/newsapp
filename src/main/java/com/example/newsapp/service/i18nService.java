package com.example.newsapp.service;

import java.util.Map;

public interface i18nService {
    Map<String, String> getMessages(String code, Object... values);
}
