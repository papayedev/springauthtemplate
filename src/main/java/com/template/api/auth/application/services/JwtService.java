package com.template.api.auth.application.services;

import com.template.api.auth.domain.model.AuthUser;
import com.template.api.auth.domain.model.User;

public interface JwtService {
    String generateAccessToken(User user);
    AuthUser parseAccessToken(String accessToken);
    String generateRefreshToken(User user);
    AuthUser parseRefreshToken(String refreshToken);
}
