package com.example.newsapp.controller;

import com.example.newsapp.dto.*;
import com.example.newsapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthRes> auth(@Valid @RequestBody AuthReq req){
        return ResponseEntity.ok(authService.authenticate(req));
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterReq req){
        return authService.register(req);
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return authService.confirm(token);
    }

    @PostMapping("/reset")
    public String reset(@Valid @RequestBody ResetPassReq req){
        return authService.resetPassword(req);
    }

    @PostMapping("/confirmReset")
    public String confirm(@RequestParam("token") String token, @RequestBody ConfirmReq req){
        return authService.confirm(token, req);
    }
}
