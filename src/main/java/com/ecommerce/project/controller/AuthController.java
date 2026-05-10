package com.ecommerce.project.controller;

import com.ecommerce.project.dto.AuthResponse;
import com.ecommerce.project.dto.LoginRequest;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.dto.RegisterRequest;
import com.ecommerce.project.service.AuthService;
import com.ecommerce.project.service.impl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    private AuthServiceImpl authService;
    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthResponse>register(@RequestBody RegisterRequest request){
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest request){
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }


}
