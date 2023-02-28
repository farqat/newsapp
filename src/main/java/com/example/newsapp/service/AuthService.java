package com.example.newsapp.service;

import com.example.newsapp.dto.*;

public interface AuthService {
    String register(RegisterReq req);
    String confirm(String token);
    AuthRes authenticate(AuthReq req);
    String resetPassword(ResetPassReq req);
    String confirm(String token, ConfirmReq req);
}
