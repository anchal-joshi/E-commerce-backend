package com.ecommerce.project.service;

import com.ecommerce.project.dto.AuthResponse;
import com.ecommerce.project.dto.LoginRequest;
import com.ecommerce.project.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
