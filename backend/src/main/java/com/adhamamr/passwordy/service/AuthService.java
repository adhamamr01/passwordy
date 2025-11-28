package com.adhamamr.passwordy.service;

import com.adhamamr.passwordy.dto.AuthResponse;
import com.adhamamr.passwordy.dto.LoginRequest;
import com.adhamamr.passwordy.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}